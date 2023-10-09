package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;

import java.io.InputStream;

public class LengthHttpResponseParser implements HttpResponseParser<Long> {
    
    @Override
    public Long parse(HttpResponse response) throws Exception {
        try (InputStream in = response.openInputStream()) {
            return response.length();
        }
    }
}