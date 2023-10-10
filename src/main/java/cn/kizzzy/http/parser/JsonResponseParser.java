package cn.kizzzy.http.parser;

import cn.kizzzy.helper.ClassHelper;
import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;
import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.lang.reflect.Type;

public abstract class JsonResponseParser<T> implements HttpResponseParser<T> {
    
    private final Type trueType;
    
    public JsonResponseParser() {
        this.trueType = ClassHelper.getGenericClass(getClass());
    }
    
    @Override
    public T parse(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream()) {
            return JSON.parseObject(is, trueType);
        }
    }
}
