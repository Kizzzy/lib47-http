package cn.kizzzy.http;

import java.io.IOException;
import java.io.InputStream;

public interface HttpCallback<T> {
    
    T doUrlExecute(InputStream in) throws IOException;
}
