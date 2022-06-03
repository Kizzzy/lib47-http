package cn.kizzzy.http;

public class HttpResult<T> {
    
    private final boolean ok;
    
    private final String msg;
    
    private final T data;
    
    public HttpResult(boolean ok, String msg, T data) {
        this.ok = ok;
        this.msg = msg;
        this.data = data;
    }
    
    public boolean isOk() {
        return ok;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public T getData() {
        return data;
    }
}
