package cn.kizzzy.http;

import java.util.Map;

public interface Http {
    
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.57";
    
    String parse(String url, Map<String, String> query);
    
    <T> T interview(HttpArgs<T> args) throws Exception;
}
