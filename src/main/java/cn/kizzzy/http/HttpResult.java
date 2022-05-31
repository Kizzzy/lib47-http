package cn.kizzzy.http;

public class HttpResult {
    
    private final boolean ok;
    
    private final String msg;
    
    private final Object data;
    
    public HttpResult(boolean ok, String msg, Object data) {
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
    
    public Object getData() {
        return data;
    }
}
