package java.net;

import cn.kizzzy.http.HttpCookieListener;

public class JdkHttpCookieJar extends InMemoryCookieStore {
    
    private final HttpCookieListener<HttpCookie> listener;
    
    public JdkHttpCookieJar() {
        this(null);
    }
    
    public JdkHttpCookieJar(HttpCookieListener<HttpCookie> listener) {
        this.listener = listener;
    }
    
    @Override
    public void add(URI uri, HttpCookie cookie) {
        super.add(uri, cookie);
        
        listener.onCookie(cookie);
    }
}
