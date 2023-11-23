package cn.kizzzy.http;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpServerFilter extends Filter {
    
    private boolean bQuery;
    
    private boolean bForm;
    
    public HttpServerFilter(boolean bQuery, boolean bForm) {
        this.bQuery = bQuery;
        this.bForm = bForm;
    }
    
    @Override
    public String description() {
        return "parse query/body to map";
    }
    
    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        if (bQuery) {
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> queryKvs = formData2Dic(query);
            for (Map.Entry<String, String> kv : queryKvs.entrySet()) {
                exchange.setAttribute(kv.getKey(), kv.getValue());
            }
        }
        
        if (bForm) {
            String body = readString(exchange.getRequestBody());
            Map<String, String> formKvs = formData2Dic(body);
            for (Map.Entry<String, String> kv : formKvs.entrySet()) {
                exchange.setAttribute(kv.getKey(), kv.getValue());
            }
        }
        
        chain.doFilter(exchange);
    }
    
    private String readString(InputStream input) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buf = new byte[8192];
            for (int n; (n = input.read(buf)) > 0; ) {
                output.write(buf, 0, n);
            }
            return output.toString();
        }
    }
    
    public static Map<String, String> formData2Dic(String formData) {
        Map<String, String> result = new HashMap<>();
        if (formData == null || formData.trim().isEmpty()) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item -> {
            final String[] keyAndVal = item.split("=");
            if (keyAndVal.length == 2) {
                try {
                    final String key = URLDecoder.decode(keyAndVal[0], "utf8");
                    final String val = URLDecoder.decode(keyAndVal[1], "utf8");
                    result.put(key, val);
                } catch (UnsupportedEncodingException e) {
                
                }
            }
        });
        return result;
    }
}
