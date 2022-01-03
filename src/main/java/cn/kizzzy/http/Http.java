package cn.kizzzy.http;

import okhttp3.HttpUrl;

import java.util.Map;

public interface Http {
    
    HttpUrl doParse(String url, Map<String, String> query);
    
    String doGet(String url, Map<String, String> query);
    
    String doGet(String url, Map<String, String> query, Map<String, String> header);
    
    String doPost(String url, Map<String, String> query, Map<String, String> form);
    
    String doPost(String url, Map<String, String> query, Map<String, String> form, Map<String, String> header);
}
