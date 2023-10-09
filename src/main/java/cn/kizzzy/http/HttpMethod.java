package cn.kizzzy.http;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    DELETE("DELETE"),
    ;
    
    private final String text;
    
    HttpMethod(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
}
