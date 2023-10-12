package java.net;

import cn.kizzzy.http.HttpAdapter;
import cn.kizzzy.http.HttpMethod;
import cn.kizzzy.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.Map;

public class JdkHttp extends HttpAdapter<HttpCookie> {
    
    protected static final Logger logger = LoggerFactory.getLogger(JdkHttp.class);
    
    private JdkHttp(Args<HttpCookie> _args) {
        super(_args);
        
        CookieManager manager = new CookieManager(
            new JdkHttpCookieJar(_args.cookieListener),
            CookiePolicy.ACCEPT_ORIGINAL_SERVER
        );
        CookieHandler.setDefault(manager);
    }
    
    @Override
    public String parse(String url, Map<String, String> query) {
        if (query != null && !query.isEmpty()) {
            StringBuilder urlBuilder = new StringBuilder(url + "?");
            for (Map.Entry<String, String> kv : query.entrySet()) {
                urlBuilder.append(String.format("%s=%s&", kv.getKey(), kv.getValue()));
            }
            url = urlBuilder.toString();
        }
        return url;
    }
    
    @Override
    protected <T> HttpResponse requestImpl(RequestArgs<T> args) throws Exception {
        String url = parse(args.url, args.queryKvs);
        logger.info("<===" + url);
        
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection(_args.proxy);
        conn.setConnectTimeout(10 * 1000);
        conn.setReadTimeout(10 * 1000);
        conn.setRequestMethod(args.method.getText());
        
        if (args.headerKvs != null) {
            args.headerKvs.forEach(conn::setRequestProperty);
        }
        
        if (args.method == HttpMethod.POST && !args.formKvs.isEmpty()) {
            conn.setDoInput(true);
            
            // todo formKvs
        }
        
        if (args.writer != null) {
            conn.setDoOutput(true);
            
            try (OutputStream os = conn.getOutputStream()) {
                args.writer.write(os);
            }
        }
        
        conn.connect();
        return new JdkHttpResponse(conn);
    }
    
    public static class Builder extends BuilderAdapter<HttpCookie, JdkHttp> {
        
        public JdkHttp build() {
            return new JdkHttp(getArgs());
        }
    }
}
