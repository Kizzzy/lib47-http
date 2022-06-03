package cn.kizzzy.http;

import java.util.Map;

public class HttpArgs<T> {
    
    public String url;
    
    public HttpMethod method;
    
    public Map<String, String> queryKvs;
    
    public Map<String, String> formKvs;
    
    public Map<String, String> headerKvs;
    
    public HttpCallback<T> callback;
    
    public HttpWriter writer;
}
