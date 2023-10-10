package cn.kizzzy.http.parser;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringResponseParser implements HttpResponseParser<String> {
    
    private final Charset charset;
    
    public StringResponseParser() {
        this(StandardCharsets.UTF_8);
    }
    
    public StringResponseParser(Charset charset) {
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