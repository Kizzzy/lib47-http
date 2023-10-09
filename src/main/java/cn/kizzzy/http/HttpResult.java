package cn.kizzzy.http;

import com.alibaba.fastjson.JSON;

public class HttpResult<T> {
    
    private final int code;
    
    private final String info;
    
    private final String msg;
    
    private final T data;
    
    private final Exception exception;
    
    public HttpResult(int code, String info, String msg, T data, Exception exception) {
        this.code = code;
        this.info = info;
        this.msg = msg;
        this.data = data;
        this.exception = exception;
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
    
    public Exception getException() {
        return exception;
    }
    
    public boolean isOk() {
        return getCode() == 200;
    }
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
