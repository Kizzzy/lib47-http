package cn.kizzzy.http;

import java.util.Map;

public class HttpArgs {
    
    public HttpType type;
    
    public Map<String, String> queryKvs;
    
    public Map<String, String> formKvs;
    
    public Map<String, String> headerKvs;
}
