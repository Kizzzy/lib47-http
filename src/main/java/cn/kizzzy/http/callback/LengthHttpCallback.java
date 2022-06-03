package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpCallback;
import cn.kizzzy.http.HttpResponse;

public class LengthHttpCallback implements HttpCallback<Long> {
    
    @Override
    public Long doUrlExecute(HttpResponse response) throws Exception {
        return response.length();
    }
}