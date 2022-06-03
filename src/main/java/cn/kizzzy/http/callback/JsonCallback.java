package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpCallback;
import cn.kizzzy.http.HttpResponse;
import com.alibaba.fastjson.JSON;

import java.io.InputStream;

public class JsonCallback<T> implements HttpCallback<T> {
    
    private final Class<T> clazz;
    
    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public T doUrlExecute(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream()) {
            return JSON.parseObject(is, clazz);
        }
    }
}
