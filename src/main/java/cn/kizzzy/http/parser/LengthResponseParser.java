package cn.kizzzy.http.parser;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;

import java.io.InputStream;

public class LengthResponseParser implements HttpResponseParser<Long> {
    
    @Override
    public Long parse(HttpResponse response) throws Exception {
        try (InputStream in = response.openInputStream()) {
            return response.length();
        }
    }
}