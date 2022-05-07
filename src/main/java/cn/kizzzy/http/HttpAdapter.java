package cn.kizzzy.http;

import java.io.IOException;
import java.util.HashMap;

public abstract class HttpAdapter implements Http {
    
    private static <T> void initialArgs(HttpArgs<T> args) {
        if (args.callback == null) {
            throw new NullPointerException("callback is null");
        }
        
        if (args.method == null) {
            args.method = HttpMethod.GET;
        }
        
        if (args.queryKvs == null) {
            args.queryKvs = new HashMap<>();
        }
        
        if (args.headerKvs == null) {
            args.headerKvs = new HashMap<>();
        }
        args.headerKvs.put("User-agent", USER_AGENT);
        
        if (args.formKvs == null) {
            args.formKvs = new HashMap<>();
        }
    }
    
    public <T> T doInterview(String url, HttpArgs<T> args) throws IOException {
        initialArgs(args);
        return doInterviewImpl(url, args);
    }
    
    protected abstract <T> T doInterviewImpl(String url, HttpArgs<T> args) throws IOException;
}
