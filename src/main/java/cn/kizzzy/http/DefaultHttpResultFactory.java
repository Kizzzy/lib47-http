package cn.kizzzy.http;

public class DefaultHttpResultFactory<T> implements HttpResultFactory<T> {
    
    @Override
    public HttpResult create(T course, boolean isOk, String msg, Object data) {
        return new HttpResult(isOk, msg, data);
    }
}
