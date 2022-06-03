package cn.kizzzy.http;

public interface HttpResultFactory {
    
    default <T> HttpResult<T> create(boolean isOk, String msg) {
        return create(isOk, msg, null);
    }
    
    default <T> HttpResult<T> create(boolean isOk, String msg, T data) {
        return new HttpResult<>(isOk, msg, data);
    }
}
