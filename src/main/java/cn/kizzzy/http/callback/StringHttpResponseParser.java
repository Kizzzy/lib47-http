package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringHttpResponseParser implements HttpResponseParser<String> {
    
    private final Charset charset;
    
    public StringHttpResponseParser() {
        this(StandardCharsets.UTF_8);
    }
    
    public StringHttpResponseParser(Charset charset) {
        this.charset = charset;
    }
    
    @Override
    public String parse(HttpResponse response) throws Exception {
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