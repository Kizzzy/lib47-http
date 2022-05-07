package cn.kizzzy.http;

import java.io.IOException;
import java.util.Map;

public interface Http {
    
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.57";
    
    String doParse(String url, Map<String, String> query);
    
    default <T> T doGet(String url, HttpArgs<T> args) throws IOException {
        args.method = HttpMethod.GET;
        return doInterview(url, args);
    }
    
    default <T> T doPost(String url, HttpArgs<T> args) throws IOException {
        args.method = HttpMethod.POST;
        return doInterview(url, args);
    }
    
    <T> T doInterview(String urlStr, HttpArgs<T> args) throws IOException;
}
