package cn.kizzzy.http;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class HttpServerBuilder {
    
    private int port;
    
    private boolean start;
    
    private Executor executor;
    
    private Object context;
    
    private Map<String, HttpHandler> handlerKvs;
    
    private List<Filter> filters;
    
    public HttpServerBuilder setPort(int port) {
        this.port = port;
        return this;
    }
    
    public HttpServerBuilder setStart(boolean start) {
        this.start = start;
        return this;
    }
    
    public HttpServerBuilder setContext(Object context) {
        this.context = context;
        return this;
    }
    
    public HttpServerBuilder setContext(String url, HttpHandler context) {
        if (handlerKvs == null) {
            handlerKvs = new HashMap<>();
        }
        handlerKvs.put(url, context);
        return this;
    }
    
    public HttpServerBuilder setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }
    
    public HttpServerBuilder setFilter(Filter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
        return this;
    }
    
    public HttpServer build() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 100);
        
        if (context != null) {
            Method[] methods = context.getClass().getDeclaredMethods();
            for (Method method : methods) {
                HttpServerRouter cmd = method.getAnnotation(HttpServerRouter.class);
                if (cmd != null) {
                    setContext(cmd.url(), exchange -> {
                        boolean accessible = method.isAccessible();
                        try {
                            method.setAccessible(true);
                            method.invoke(context, exchange);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        } finally {
                            method.setAccessible(accessible);
                        }
                    });
                }
            }
        }
        
        if (handlerKvs != null && !handlerKvs.isEmpty()) {
            for (Map.Entry<String, HttpHandler> kv : handlerKvs.entrySet()) {
                HttpContext c = httpServer.createContext(kv.getKey(), kv.getValue());
                
                if (filters != null && !filters.isEmpty()) {
                    c.getFilters().addAll(filters);
                }
            }
        }
        
        if (executor != null) {
            httpServer.setExecutor(executor);
        }
        
        if (start) {
            httpServer.start();
        }
        
        return httpServer;
    }
}
