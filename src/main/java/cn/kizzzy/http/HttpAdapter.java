package cn.kizzzy.http;

import cn.kizzzy.helper.StringHelper;

import java.util.HashMap;

public abstract class HttpAdapter implements Http {
    
    private final String userAgent;
    
    public HttpAdapter() {
        this(USER_AGENT);
    }
    
    public HttpAdapter(String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Override
    public <T> T interview(HttpArgs<T> args) throws Exception {
        initialHttpArgs(args);
        return interviewImpl(args);
    }
    
    protected abstract <T> T interviewImpl(HttpArgs<T> args) throws Exception;
    
    private <T> void initialHttpArgs(HttpArgs<T> args) {
        if (StringHelper.isNullOrEmpty(args.url)) {
            throw new NullPointerException("url is null: " + args.url);
        }
        
        if (args.callback == null) {
            throw new NullPointerException("callback is null");
        }
        
        if (args.method == null) {
            args.method = HttpMethod.GET;
        }
        
        if (args.headerKvs == null) {
            args.headerKvs = new HashMap<>();
        }
        args.headerKvs.put("User-Agent", userAgent);
    }
}
