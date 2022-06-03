package cn.kizzzy.http.jdk;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.http.HttpAdapter;
import cn.kizzzy.http.HttpArgs;
import cn.kizzzy.http.HttpMethod;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class JdkHttp extends HttpAdapter {
    
    public JdkHttp() {
    
    }
    
    public JdkHttp(String userAgent) {
        super(userAgent);
    }
    
    @Override
    public String parse(String url, Map<String, String> query) {
        if (query != null && query.size() > 0) {
            StringBuilder urlBuilder = new StringBuilder(url + "?");
            for (Map.Entry<String, String> kv : query.entrySet()) {
                urlBuilder.append(String.format("%s=%s&", kv.getKey(), kv.getValue()));
            }
            url = urlBuilder.toString();
        }
        return url;
    }
    
    @Override
    protected <T> T interviewImpl(HttpArgs<T> args) throws Exception {
        String url = parse(args.url, args.queryKvs);
        LogHelper.info("<===" + url);
        
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        conn.setConnectTimeout(10 * 1000);
        conn.setReadTimeout(10 * 1000);
        conn.setRequestMethod(args.method.getText());
        
        if (args.headerKvs != null) {
            args.headerKvs.forEach(conn::setRequestProperty);
        }
        
        //args.formKvs.forEach(conn::setRequestProperty);
        
        if (args.method == HttpMethod.POST) {
            conn.setDoInput(true);
            conn.setDoOutput(true);
        }
        
        if (args.writer != null) {
            args.writer.write(conn.getOutputStream());
        }
        
        conn.connect();
        
        if (conn.getResponseCode() == 200) {
            return args.callback.doUrlExecute(new JdkHttpResponse(conn));
        }
        throw new IOException(String.format("%s interview error: %s", args.url, conn.getResponseCode()));
    }
}
