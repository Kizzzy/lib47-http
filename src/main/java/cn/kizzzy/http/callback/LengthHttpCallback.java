package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpCallback;
import cn.kizzzy.http.HttpResponse;

import java.io.IOException;

public class LengthHttpCallback implements HttpCallback<Long> {
    
    @Override
    public Long doUrlExecute(HttpResponse response) throws IOException {
        return response.length();
    }
}