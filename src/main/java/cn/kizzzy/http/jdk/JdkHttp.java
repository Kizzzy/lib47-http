package cn.kizzzy.http.jdk;

import cn.kizzzy.http.HttpAdapter;
import cn.kizzzy.http.HttpArgs;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class JdkHttp extends HttpAdapter {
    
    @Override
    public String doParse(String url, Map<String, String> query) {
        if (query.size() > 0) {
            StringBuilder urlBuilder = new StringBuilder(url + "?");
            for (Map.Entry<String, String> kv : query.entrySet()) {
                urlBuilder.append(String.format("%s=%s&", kv.getKey(), kv.getValue()));
            }
            url = urlBuilder.toString();
        }
        return url;
    }
    
    @Override
    protected <T> T doInterviewImpl(String url, HttpArgs<T> args) throws IOException {
        URL _url = new URL(doParse(url, args.queryKvs));
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        conn.setConnectTimeout(10 * 1000);
        conn.setReadTimeout(10 * 1000);
        conn.setRequestMethod(args.method.getText());
        args.headerKvs.forEach(conn::setRequestProperty);
        //args.formKvs.forEach(conn::setRequestProperty);
        if (conn.getResponseCode() == 200) {
            return args.callback.doUrlExecute(conn.getInputStream());
        }
        throw new IOException(String.format("%s interview error: %s", url, conn.getResponseCode()));
    }
}
