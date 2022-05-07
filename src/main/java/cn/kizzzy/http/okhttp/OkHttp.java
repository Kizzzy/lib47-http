package cn.kizzzy.http.okhttp;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.http.HttpAdapter;
import cn.kizzzy.http.HttpArgs;
import cn.kizzzy.http.HttpMethod;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Map;

public class OkHttp extends HttpAdapter {
    
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
    public String doParse(String url, Map<String, String> query) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        query.forEach(urlBuilder::addQueryParameter);
        return urlBuilder.build().toString();
    }
    
    @Override
    protected <T> T doInterviewImpl(String url, HttpArgs<T> args) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        args.formKvs.forEach(formBuilder::add);
        
        url = doParse(url, args.queryKvs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        
        if (args.method == HttpMethod.GET) {
            requestBuilder.get();
        } else if (args.method == HttpMethod.POST) {
            requestBuilder.post(formBuilder.build());
        } else if (args.method == HttpMethod.HEAD) {
            requestBuilder.head();
        }
        
        LogHelper.info("<===" + url);
        
        args.headerKvs.forEach(requestBuilder::header);
        
        Call call = httpClient.newCall(requestBuilder.build());
        
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        if (response.code() == 200 && responseBody != null) {
            return args.callback.doUrlExecute(responseBody.byteStream());
        }
        throw new IOException(String.format("%s interview error: %s", url, response.code()));
    }
}
