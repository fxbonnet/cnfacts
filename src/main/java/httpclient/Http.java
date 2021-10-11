package httpclient;

import inject.Component;
import util.Log;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static util.Exceptions.sneak;

public class Http {
    public static final Component<Http> COMPONENT = new Component<>(Http.class, Http::new);
    private static final Log log = Log.get(Http.class);
    public static final Duration timeout = Duration.of(5, ChronoUnit.SECONDS);
    private final HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(timeout).build();
    public String get(String url, Header... headers) {
        var request = request().GET().uri(URI.create(url));
        for (var header : headers) {
            request.header(header.name, header.value);
        }
        return send(request.build());
    }
    private HttpRequest.Builder request() {return HttpRequest.newBuilder().timeout(timeout);}
    public String post(String url, String payload, Header... headers) {
        var request = request().POST(HttpRequest.BodyPublishers.ofString(payload)).uri(URI.create(url));
        for (var header : headers) {
            request.header(header.name, header.value);
        }
        return send(request.build());
    }
    private String send(HttpRequest request) {
        try {
            var start = System.currentTimeMillis();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var duration = System.currentTimeMillis() - start;
            log.debug(request.method() + " " + request.uri() + " -> " + response.statusCode() + " in " + duration + " ms");
            if (response.statusCode() == 200)
                return response.body();
            else
                throw new HttpError(request.uri().toString(), response);
        } catch (IOException | InterruptedException e) {
            throw sneak(e);
        }
    }
}
