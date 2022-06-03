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
        this(USER_AGENT, null);
    }
    
    public OkHttp(CookieJar cookieJar) {
        this(USER_AGENT, cookieJar);
    }
    
    public OkHttp(String userAgent) {
        this(userAgent, null);
    }
    
    public OkHttp(String userAgent, CookieJar cookieJar) {
        super(userAgent);
        
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        httpClient = builder.build();
    }
    
    @Override
    public String parse(String url, Map<String, String> query) {
        if (query != null && query.size() > 0) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            query.forEach(urlBuilder::addQueryParameter);
            return urlBuilder.build().toString();
        }
        return url;
    }
    
    @Override
    protected <T> T interviewImpl(HttpArgs<T> args) throws Exception {
        String url = parse(args.url, args.queryKvs);
        LogHelper.info("<===" + url);
        
        Request.Builder requestBuilder = new Request.Builder().url(url);
        
        if (args.method == HttpMethod.GET) {
            requestBuilder.get();
        } else if (args.method == HttpMethod.POST) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            if (args.formKvs != null) {
                args.formKvs.forEach(formBuilder::add);
            }
            requestBuilder.post(formBuilder.build());
        } else if (args.method == HttpMethod.HEAD) {
            requestBuilder.head();
        }
        
        if (args.headerKvs != null) {
            args.headerKvs.forEach(requestBuilder::header);
        }
        
        Call call = httpClient.newCall(requestBuilder.build());
        
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        if (response.code() == 200 && responseBody != null) {
            return args.callback.doUrlExecute(new OkHttpResponse(response));
        }
        throw new IOException(String.format("%s interview error: %s", url, response.code()));
    }
}
