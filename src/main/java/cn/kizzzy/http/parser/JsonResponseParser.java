package cn.kizzzy.http.parser;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.InputStream;

public abstract class JsonResponseParser<T> extends TypeReference<T> implements HttpResponseParser<T> {
    
    @Override
    public T parse(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream()) {
            return JSON.parseObject(is, getType());
        }
    }
    
    public T parse(String response) throws Exception {
        return JSON.parseObject(response, getType());
    }
}
