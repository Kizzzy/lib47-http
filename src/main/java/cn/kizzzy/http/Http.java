package cn.kizzzy.http;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface Http {
    
    class RequestArgs<T> {
        
        public String url;
        
        public String info = "";
        
        public HttpMethod method;
        
        public Map<String, String> queryKvs;
        
        public Map<String, String> formKvs;
        
        public Map<String, String> headerKvs;
        
        public HttpResponseParser<T> parser;
        
        public Consumer<T> callback;
        
        public HttpWriter writer;
        
        public Consumer<RequestBuilder<T>> handler;
    }
    
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.57";
    
    String parse(String url, Map<String, String> query);
    
    <T> HttpResult<T> request(RequestArgs<T> args) throws Exception;
    
    class RequestBuilder<T> {
        
        private RequestArgs<T> args;
        
        protected RequestArgs<T> getArgs() {
            if (args == null) {
                args = new RequestArgs<>();
            }
            return args;
        }
        
        protected Map<String, String> getQueryKvs() {
            Map<String, String> kvs = getArgs().queryKvs;
            if (kvs == null) {
                getArgs().queryKvs = kvs = new HashMap<>();
            }
            return kvs;
        }
        
        protected Map<String, String> getFormKvs() {
            Map<String, String> kvs = getArgs().formKvs;
            if (kvs == null) {
                getArgs().formKvs = kvs = new HashMap<>();
            }
            return kvs;
        }
        
        protected Map<String, String> getHeaderKvs() {
            Map<String, String> kvs = getArgs().headerKvs;
            if (kvs == null) {
                getArgs().headerKvs = kvs = new HashMap<>();
            }
            return kvs;
        }
        
        public RequestBuilder<T> setUrl(String url) {
            getArgs().url = url;
            return this;
        }
        
        public RequestBuilder<T> setInfo(String info) {
            getArgs().info = info;
            return this;
        }
        
        public RequestBuilder<T> setMethod(HttpMethod method) {
            getArgs().method = method;
            return this;
        }
        
        public RequestBuilder<T> setQueryKvs(Map<String, String> queryKvs) {
            getArgs().queryKvs = queryKvs;
            return this;
        }
        
        public RequestBuilder<T> addQuery(String key, Object value) {
            getQueryKvs().put(key, String.valueOf(value));
            return this;
        }
        
        public RequestBuilder<T> addQueryIf(String key, Object value, boolean b) {
            if (b) {
                getQueryKvs().put(key, String.valueOf(value));
            }
            return this;
        }
        
        public RequestBuilder<T> setFormKvs(Map<String, String> formKvs) {
            getArgs().formKvs = formKvs;
            return this;
        }
        
        public RequestBuilder<T> addForm(String key, Object value) {
            getFormKvs().put(key, String.valueOf(value));
            return this;
        }
        
        public RequestBuilder<T> addFormIf(String key, Object value, boolean b) {
            if (b) {
                getFormKvs().put(key, String.valueOf(value));
            }
            return this;
        }
        
        public RequestBuilder<T> setHeaderKvs(Map<String, String> headerKvs) {
            getArgs().headerKvs = headerKvs;
            return this;
        }
        
        public RequestBuilder<T> addHeader(String key, Object value) {
            getHeaderKvs().put(key, String.valueOf(value));
            return this;
        }
        
        public RequestBuilder<T> addHeaderIf(String key, Object value, boolean b) {
            if (b) {
                getHeaderKvs().put(key, String.valueOf(value));
            }
            return this;
        }
        
        public RequestBuilder<T> setParser(HttpResponseParser<T> parser) {
            getArgs().parser = parser;
            return this;
        }
        
        public RequestBuilder<T> setCallback(Consumer<T> callback) {
            getArgs().callback = callback;
            return this;
        }
        
        public RequestBuilder<T> setWriter(HttpWriter writer) {
            getArgs().writer = writer;
            return this;
        }
        
        public RequestBuilder<T> setHandler(Consumer<RequestBuilder<T>> handler) {
            getArgs().handler = handler;
            return this;
        }
        
        public HttpResult<T> delete(Http http) throws Exception {
            getArgs().method = HttpMethod.DELETE;
            return request(http);
        }
        
        public HttpResult<T> get(Http http) throws Exception {
            getArgs().method = HttpMethod.GET;
            return request(http);
        }
        
        public HttpResult<T> head(Http http) throws Exception {
            getArgs().method = HttpMethod.HEAD;
            return request(http);
        }
        
        public HttpResult<T> post(Http http) throws Exception {
            getArgs().method = HttpMethod.POST;
            return request(http);
        }
        
        public HttpResult<T> request(Http http) throws Exception {
            if (getArgs().handler != null) {
                getArgs().handler.accept(this);
            }
            return http.request(getArgs());
        }
    }
}
