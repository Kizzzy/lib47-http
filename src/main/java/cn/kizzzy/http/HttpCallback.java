package cn.kizzzy.http;

import java.io.IOException;

public interface HttpCallback<T> {
    
    T doUrlExecute(HttpResponse response) throws IOException;
}
