package cn.kizzzy.http;

public interface HttpCallback<T> {
    
    T doUrlExecute(HttpResponse response) throws Exception;
}
