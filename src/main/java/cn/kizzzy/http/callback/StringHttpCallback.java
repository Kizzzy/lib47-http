package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpCallback;
import cn.kizzzy.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringHttpCallback implements HttpCallback<String> {
    
    private final Charset charset;
    
    public StringHttpCallback() {
        this(StandardCharsets.UTF_8);
    }
    
    public StringHttpCallback(Charset charset) {
        this.charset = charset;
    }
    
    @Override
    public String doUrlExecute(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1 << 20];
            for (int len, total = 0; (len = is.read(buf)) != -1; ) {
                os.write(buf, 0, len);
                total += len;
            }
            return new String(os.toByteArray(), charset);
        }
    }
}