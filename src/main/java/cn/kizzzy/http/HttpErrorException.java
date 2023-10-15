package cn.kizzzy.http;

public class HttpErrorException extends RuntimeException {
    
    public HttpErrorException(String info, Throwable cause) {
        super(String.format("%s 请求失败, %s", info, cause.getMessage()), cause);
    }
}
