package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpCallback;
import cn.kizzzy.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BytesHttpCallback implements HttpCallback<byte[]> {
    
    @Override
    public byte[] doUrlExecute(HttpResponse response) throws IOException {
        try (InputStream is = response.openInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1 << 20];
            for (int len, total = 0; (len = is.read(buf)) != -1; ) {
                os.write(buf, 0, len);
                total += len;
            }
            return os.toByteArray();
        }
    }
}