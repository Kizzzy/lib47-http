package cn.kizzzy.http;

import cn.kizzzy.helper.LogHelper;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OkHttp implements Http {
    
    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.57";
    
    private OkHttpClient httpClient;
    
    public OkHttp() {
        this(null);
    }
    
    public OkHttp(CookieJar cookieJar) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        httpClient = builder.build();
    }
    
    @Override
    public HttpUrl doParse(String url, Map<String, String> query) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        query.forEach(urlBuilder::addQueryParameter);
        return urlBuilder.build();
    }
    
    @Override
    public String doGet(String url, Map<String, String> query) {
        return doGet(url, query, new HashMap<>());
    }
    
    @Override
    public String doGet(String url, Map<String, String> query, Map<String, String> header) {
        try {
            Request.Builder requestBuilder = new Request.Builder()
                .url(doParse(url, query))
                .header("User-Agent", userAgent)
                .get();
            
            LogHelper.info("<===" + url);
            
            header.forEach(requestBuilder::header);
            
            Call call = httpClient.newCall(requestBuilder.build());
            
            String response = Objects.requireNonNull(call.execute().body()).string();
            
            if (response.length() < 1000) {
                LogHelper.info("===>" + response);
            }
            
            return response;
        } catch (IOException e) {
            throw new RuntimeException("do http request error", e);
        }
    }
    
    @Override
    public String doPost(String url, Map<String, String> query, Map<String, String> form) {
        return doPost(url, query, form, new HashMap<>());
    }
    
    @Override
    public String doPost(String url, Map<String, String> query, Map<String, String> form, Map<String, String> header) {
        try {
            FormBody.Builder formBuilder = new FormBody.Builder();
            form.forEach(formBuilder::add);
            
            Request.Builder requestBuilder = new Request.Builder()
                .url(doParse(url, query))
                .header("User-Agent", userAgent)
                .post(formBuilder.build());
            
            LogHelper.info("<===" + url);
            
            header.forEach(requestBuilder::header);
            
            Call call = httpClient.newCall(requestBuilder.build());
            
            String response = Objects.requireNonNull(call.execute().body()).string();
            
            if (response.length() < 1000) {
                LogHelper.info("===>" + response);
            }
            
            return response;
        } catch (IOException e) {
            throw new RuntimeException("do http request error", e);
        }
    }
}
