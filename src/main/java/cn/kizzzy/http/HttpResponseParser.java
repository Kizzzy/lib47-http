package cn.kizzzy.http;

public interface HttpResponseParser<T> {
    
    T parse(HttpResponse response) throws Exception;
}
