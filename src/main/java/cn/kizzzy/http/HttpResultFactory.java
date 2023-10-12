package cn.kizzzy.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResultFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpResultFactory.class);
    
    public <T> HttpResult<T> create(boolean isOk, String msg, T data) {
        // todo
        return new HttpResult<>(isOk ? 200 : -1, "", msg, data, null);
    }
    
    public <T> HttpResult<T> create(int code, String info, String msg, T data, Exception exception) {
        if (exception != null) {
            logger.error(String.format("%s <%d, %s>", info, code, msg), exception);
        } else {
            logger.info(String.format("%s <%d, %s>", info, code, msg));
        }
        
        return new HttpResult<>(code, info, msg, data, exception);
    }
}
