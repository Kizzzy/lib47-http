package cn.kizzzy.http.callback;

import cn.kizzzy.http.HttpResponse;
import cn.kizzzy.http.HttpResponseParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class LinesHttpResponseParser implements HttpResponseParser<List<String>> {
    
    @Override
    public List<String> parse(HttpResponse response) throws Exception {
        final List<String> temps = new LinkedList<>();
        try (InputStream is = response.openInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                temps.add(line);
            }
            return temps;
        }
    }
}