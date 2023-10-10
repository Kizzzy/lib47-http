package cn.kizzzy.http.parser;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class BytesResponseParser implements HttpResponseParser<byte[]> {
    
    private final int limitSize;
    
    public BytesResponseParser() {
        this(-1);
    }
    
    public BytesResponseParser(int limitSize) {
        this.limitSize = limitSize;
    }
    
    @Override
    public byte[] parse(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1 << 20];
            for (int len, total = 0; (len = is.read(buf)) != -1; ) {
                bos.write(buf, 0, len);
                total += len;
            }
            
            byte[] bytes = bos.toByteArray();
            if (limitSize >= 0 && bytes.length != limitSize) {
                throw new RuntimeException("get bytes error, expected: " + limitSize + ", get: " + bytes.length);
            }
            return bytes;
        }
    }
}