package httpserver;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;
import util.Exceptions;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Request {
    private final HttpExchange exchange;
    Request(HttpExchange exchange) {
        this.exchange = exchange;
    }
    public String getPathToken(int position) {
        return exchange.getRequestURI().getPath().split("/")[position + 1];
    }
    public Map<String, List<String>> decodeBody() {
        try {
            var requestBody = IOUtils.toString(exchange.getRequestBody(), UTF_8);
            return Pattern.compile("&")
                    .splitAsStream(requestBody)
                    .map(s -> Arrays.copyOf(s.split("=", 2), 2))
                    .collect(Collectors.groupingBy(s -> decode(s[0]), Collectors.mapping(s -> decode(s[1]), Collectors.toList())));
        } catch (IOException e) {
            throw Exceptions.sneak(e);
        }
    }
    private static String decode(String encoded) {
        return URLDecoder.decode(encoded, UTF_8);
    }
    public String getHeader(String name) {
        var values = exchange.getRequestHeaders().get(name);
        if (values == null || values.isEmpty())
            return null;
        else if (values.size() == 1)
            return values.get(0);
        else
            throw new IllegalArgumentException("Multiple values for header " + name);
    }
    public String getQueryParameter(String name) {return parseQueryParameters().get(name);}
    public Map<String, String> parseQueryParameters() {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                result.put(decode(pair.substring(0, idx)), decode(pair.substring(idx + 1)));
            }
        }
        return result;
    }
}
