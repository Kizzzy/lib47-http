package okhttp3;

import cn.kizzzy.http.HttpCookieListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OkHttpCookieJar implements CookieJar {
    
    private final Map<String, List<Cookie>> cookieStore =
        new ConcurrentHashMap<>();
    
    private HttpCookieListener<Cookie> listener;
    
    public OkHttpCookieJar() {
        this(null);
    }
    
    public OkHttpCookieJar(HttpCookieListener<Cookie> listener) {
        this.listener = listener;
    }
    
    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<>();
    }
    
    @Override
    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            if (listener != null) {
                listener.onCookie(cookie);
            }
        }
        cookieStore.put(url.host(), cookies);
    }
    
    public void load(String url, Map<String, String> kvs) {
        List<Cookie> list = cookieStore.computeIfAbsent(url, k -> new LinkedList<>());
        for (Map.Entry<String, String> entry : kvs.entrySet()) {
            Cookie cookie = new Cookie.Builder()
                .name(entry.getKey())
                .value(entry.getValue())
                .build();
            list.add(cookie);
        }
    }
}
