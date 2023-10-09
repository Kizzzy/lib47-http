package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.io.InputStream;

public class JsonResponseParser<T> implements HttpResponseParser<T> {
    
    @Override
    public T parse(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream()) {
            return JSON.parseObject(is, new TypeReference<T>() {
            }.getType());
        }
    }
}
