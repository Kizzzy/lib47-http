package cn.kizzzy.http;

public class HttpResult<T> {
    
    private final int code;
    
    private final String info;
    
    private final String msg;
    
    private final T data;
    
    public HttpResult(int code, String info, String msg, T data) {
        this.code = code;
        this.info = info;
        this.msg = msg;
        this.data = data;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getInfo() {
        return info;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public T getData() {
        return data;
    }
    
    public boolean isOk() {
        return getCode() == 200;
    }
}
