package cn.kizzzy.http;

import java.io.OutputStream;

public interface HttpWriter {
    
    void write(OutputStream writer) throws Exception;
}
