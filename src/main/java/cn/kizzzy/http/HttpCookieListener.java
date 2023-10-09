package cn.kizzzy.http;

public interface HttpCookieListener<T> {
    
    void onCookie(T cookie);
}
