package cn.kizzzy.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResultFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpResultFactory.class);
    
    public <T> HttpResult<T> create(boolean isOk, String msg, T data) {
        // todo
        return new HttpResult<>(isOk ? 200 : -1, "", msg, data);
    }
}
