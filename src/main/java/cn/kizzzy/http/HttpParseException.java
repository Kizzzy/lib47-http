package cn.kizzzy.http;

public class HttpParseException extends RuntimeException {
    
    public HttpParseException(String info, Throwable cause) {
        super(String.format("%s 解析错误", info), cause);
    }
}
