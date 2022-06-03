package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpCallback;
import cn.kizzzy.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;

public class JsonCallback2 implements HttpCallback<JSONObject> {
    
    @Override
    public JSONObject doUrlExecute(HttpResponse response) throws Exception {
        try (InputStream is = response.openInputStream()) {
            return JSON.parseObject(is, JSONObject.class);
        }
    }
}
