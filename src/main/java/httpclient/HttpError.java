package httpclient;

import java.net.http.HttpResponse;

class HttpError extends RuntimeException {
    HttpError(String url, HttpResponse<String> response) {
        super(String.format("%s -> %s %s", url, response.statusCode(), response.body()));
    }
}
