package okhttp3;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.http.HttpAdapter;
import cn.kizzzy.http.HttpMethod;
import cn.kizzzy.http.HttpResponse;

import java.util.Map;

public class OkHttp extends HttpAdapter<Cookie> {
    
    private final OkHttpClient httpClient;
    
    private OkHttp(Args<Cookie> _args) {
        super(_args);
        
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new OkHttpCookieJar(_args.cookieListener));
        if (_args.proxy != null) {
            builder.proxy(_args.proxy);
        }
        if (_args.proxySelector != null) {
            builder.proxySelector(_args.proxySelector);
        }
        httpClient = builder.build();
    }
    
    @Override
    public String parse(String url, Map<String, String> query) {
        if (query != null && !query.isEmpty()) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            query.forEach(urlBuilder::addQueryParameter);
            return urlBuilder.build().toString();
        }
        return url;
    }
    
    @Override
    protected <T> HttpResponse requestImpl(RequestArgs<T> args) throws Exception {
        String url = parse(args.url, args.queryKvs);
        LogHelper.info("<===" + url);
        
        Request.Builder requestBuilder = new Request.Builder().url(url);
        
        if (args.headerKvs != null) {
            args.headerKvs.forEach(requestBuilder::header);
        }
        
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
        
        // todo writer
        
        Call call = httpClient.newCall(requestBuilder.build());
        Response response = call.execute();
        return new OkHttpResponse(response);
    }
    
    public static class Builder extends BuilderAdapter<Cookie, OkHttp> {
        
        public OkHttp build() {
            return new OkHttp(getArgs());
        }
    }
}
