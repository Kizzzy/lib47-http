package okhttp3;

import cn.kizzzy.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class OkHttpResponse implements HttpResponse {
    
    private final Response response;
    
    public OkHttpResponse(Response response) {
        this.response = response;
    }
    
    @Override
    public int code() throws IOException {
        return response.code();
    }
    
    @Override
    public long length() {
        return response.body().contentLength();
    }
    
    @Override
    public InputStream openInputStream() throws IOException {
        return response.body().byteStream();
    }
}
