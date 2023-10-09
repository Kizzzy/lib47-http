package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;

public class JsonResponseParser2 implements HttpResponseParser<JSONObject> {
    
    @Override
    public JSONObject parse(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream()) {
            return JSON.parseObject(is, JSONObject.class);
        }
    }
}
