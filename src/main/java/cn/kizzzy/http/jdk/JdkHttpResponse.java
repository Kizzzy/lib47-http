package cn.kizzzy.http.jdk;

import cn.kizzzy.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class JdkHttpResponse implements HttpResponse {
    
    private final HttpURLConnection conn;
    
    public JdkHttpResponse(HttpURLConnection conn) {
        this.conn = conn;
    }
    
    @Override
    public int code() throws IOException {
        return conn.getResponseCode();
    }
    
    @Override
    public long length() {
        return conn.getContentLength();
    }
    
    @Override
    public InputStream openInputStream() throws IOException {
        return conn.getInputStream();
    }
}
