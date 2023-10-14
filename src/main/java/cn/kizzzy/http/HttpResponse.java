package cn.kizzzy.http;

import java.io.IOException;
import java.io.InputStream;

public interface HttpResponse extends AutoCloseable {
    
    int code() throws IOException;
    
    long length();
    
    InputStream openInputStream() throws IOException;
}
