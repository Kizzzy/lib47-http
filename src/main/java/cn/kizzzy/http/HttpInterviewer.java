package cn.kizzzy.http;

import java.util.Map;

public class HttpInterviewer<T> {
    
    private String url;
    
    private HttpMethod method;
    
    private Map<String, String> queryKvs;
    
    private Map<String, String> formKvs;
    
    private Map<String, String> headerKvs;
    
    private HttpCallback<T> callback;
    
    private HttpWriter writer;
    
    public T get(Http http) throws Exception {
        method = HttpMethod.GET;
        return interview(http);
    }
    
    public T post(Http http) throws Exception {
        method = HttpMethod.POST;
        return interview(http);
    }
    
    public T interview(Http http) throws Exception {
        HttpArgs<T> args = new HttpArgs<>();
        args.url = url;
        args.method = method;
        args.queryKvs = queryKvs;
        args.formKvs = formKvs;
        args.headerKvs = headerKvs;
        args.callback = callback;
        args.writer = writer;
        return http.interview(args);
    }
    
    public HttpInterviewer<T> setUrl(String url) {
        this.url = url;
        return this;
    }
    
    public HttpInterviewer<T> setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }
    
    public HttpInterviewer<T> setQueryKvs(Map<String, String> queryKvs) {
        this.queryKvs = queryKvs;
        return this;
    }
    
    public HttpInterviewer<T> setFormKvs(Map<String, String> formKvs) {
        this.formKvs = formKvs;
        return this;
    }
    
    public HttpInterviewer<T> setHeaderKvs(Map<String, String> headerKvs) {
        this.headerKvs = headerKvs;
        return this;
    }
    
    public HttpInterviewer<T> setCallback(HttpCallback<T> callback) {
        this.callback = callback;
        return this;
    }
    
    public HttpInterviewer<T> setWriter(HttpWriter writer) {
        this.writer = writer;
        return this;
    }
}
