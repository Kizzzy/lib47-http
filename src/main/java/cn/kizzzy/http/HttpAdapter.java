package cn.kizzzy.http;

import cn.kizzzy.helper.StringHelper;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.HashMap;
import java.util.Objects;

public abstract class HttpAdapter<Cookie> implements Http {
    
    public static class Args<Cookie> {
        
        public String userAgent = USER_AGENT;
        
        public Proxy proxy;
        
        public ProxySelector proxySelector;
        
        public HttpCookieListener<Cookie> cookieListener;
    }
    
    protected final Args<Cookie> _args;
    
    protected HttpAdapter(Args<Cookie> _args) {
        Objects.requireNonNull(_args);
        this._args = _args;
    }
    
    @Override
    public <T> HttpResult<T> request(RequestArgs<T> args) throws Exception {
        if (StringHelper.isNullOrEmpty(args.url)) {
            throw new NullPointerException("url is null: " + args.url);
        }
        
        if (args.parser == null) {
            throw new NullPointerException("parser is null");
        }
        
        if (args.method == null) {
            args.method = HttpMethod.GET;
        }
        
        if (args.headerKvs == null) {
            args.headerKvs = new HashMap<>();
        }
        
        args.headerKvs.put("User-Agent", this._args.userAgent);
        
        try (HttpResponse response = requestImpl(args)) {
            T result = args.parser.parse(response);
            if (args.callback != null) {
                args.callback.accept(result);
            }
            return new HttpResult<>(response.code(), args.info, "请求成功", result);
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s 解析错误", args.info), e);
        }
    }
    
    protected abstract <T> HttpResponse requestImpl(RequestArgs<T> args) throws Exception;
    
    public abstract static class BuilderAdapter<Cookie, T extends HttpAdapter<Cookie>> {
        
        private Args<Cookie> args;
        
        protected Args<Cookie> getArgs() {
            if (args == null) {
                args = new Args<>();
            }
            return args;
        }
        
        public String getUserAgent() {
            return getArgs().userAgent;
        }
        
        public BuilderAdapter<Cookie, T> setUserAgent(String userAgent) {
            getArgs().userAgent = userAgent;
            return this;
        }
        
        public Proxy getProxy() {
            return getArgs().proxy;
        }
        
        public BuilderAdapter<Cookie, T> setProxy(Proxy proxy) {
            getArgs().proxy = proxy;
            return this;
        }
        
        public ProxySelector getProxySelector() {
            return getArgs().proxySelector;
        }
        
        public BuilderAdapter<Cookie, T> setProxySelector(ProxySelector proxySelector) {
            getArgs().proxySelector = proxySelector;
            return this;
        }
        
        public HttpCookieListener<Cookie> getCookieListener() {
            return getArgs().cookieListener;
        }
        
        public BuilderAdapter<Cookie, T> setCookieListener(HttpCookieListener<Cookie> cookieListener) {
            getArgs().cookieListener = cookieListener;
            return this;
        }
        
        public abstract T build();
    }
}
