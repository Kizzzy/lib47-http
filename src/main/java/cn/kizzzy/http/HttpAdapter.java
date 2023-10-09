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
        
        public HttpResultFactory resultFactory = DEFAULT_RESULT_FACTORY;
    }
    
    protected final Args<Cookie> _args;
    
    protected HttpAdapter(Args<Cookie> _args) {
        Objects.requireNonNull(_args);
        this._args = _args;
    }
    
    @Override
    public <T> HttpResult<T> request(RequestArgs<T> args) {
        try {
            if (StringHelper.isNullOrEmpty(args.url)) {
                throw new NullPointerException("url is null: " + args.url);
            }
            
            if (args.parser == null) {
                throw new NullPointerException("callback is null");
            }
            
            if (args.method == null) {
                args.method = HttpMethod.GET;
            }
            
            if (args.headerKvs == null) {
                args.headerKvs = new HashMap<>();
            }
            
            args.headerKvs.put("User-Agent", this._args.userAgent);
            
            HttpResponse response = requestImpl(args);
            
            try {
                T result = args.parser.parse(response);
                if (args.callback != null) {
                    args.callback.accept(result);
                }
                return _args.resultFactory.create(response.code(), args.info, "请求成功", result, null);
            } catch (Exception e) {
                return _args.resultFactory.create(response.code(), args.info, "解析错误", null, e);
            }
        } catch (Exception e) {
            return _args.resultFactory.create(-1, args.info, "请求错误", null, e);
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
        
        public BuilderAdapter<Cookie, T> setResultFactory(HttpResultFactory resultFactory) {
            getArgs().resultFactory = resultFactory;
            return this;
        }
        
        public HttpResultFactory getResultFactory() {
            return getArgs().resultFactory;
        }
        
        public abstract T build();
    }
}
