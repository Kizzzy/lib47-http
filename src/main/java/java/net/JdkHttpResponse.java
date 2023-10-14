package java.net;

import cn.kizzzy.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

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
    
    @Override
    public void close() throws Exception {
        conn.disconnect();
    }
}
