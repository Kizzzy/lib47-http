package cn.kizzzy.http;

public interface HttpResultFactory<T> {
    
    default HttpResult create(boolean isOk, String msg) {
        return create(null, isOk, msg, null);
    }
    
    default HttpResult create(boolean isOk, String msg, Object data) {
        return create(null, isOk, msg, data);
    }
    
    default HttpResult create(T course, boolean isOk, String msg) {
        return create(course, isOk, msg, null);
    }
    
    HttpResult create(T course, boolean isOk, String msg, Object data);
}
