package cn.kizzzy.http;

import cn.kizzzy.helper.LogHelper;

public class HttpResultFactory {
    
    public <T> HttpResult<T> create(boolean isOk, String msg, T data) {
        // todo
        return new HttpResult<>(isOk ? 200 : -1, "", msg, data, null);
    }
    
    public <T> HttpResult<T> create(int code, String info, String msg, T data, Exception exception) {
        if (exception != null) {
            LogHelper.error(String.format("%s <%d, %s>", info, code, msg), exception);
        } else {
            LogHelper.info(String.format("%s <%d, %s>", info, code, msg));
        }
        
        return new HttpResult<>(code, info, msg, data, exception);
    }
}
