package cn.kizzzy.http.okhttp;

import cn.kizzzy.helper.LogHelper;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OkHttpCookieJar implements CookieJar {
    
    private final Map<String, List<Cookie>> cookieStore =
        new ConcurrentHashMap<>();
    
    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<>();
    }
    
    @Override
    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            processCookie(cookie);
        }
        cookieStore.put(url.host(), cookies);
    }
    
    protected void processCookie(Cookie cookie) {
        LogHelper.info("===> key: " + cookie.name() + ", value: " + cookie.value());
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
