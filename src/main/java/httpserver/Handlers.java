package httpserver;

import java.util.Objects;
import java.util.function.BiFunction;

import static httpserver.Html.body;
import static httpserver.Html.head;
import static httpserver.Html.html;
import static httpserver.Html.title;
import static httpserver.Tag.tag;

public class Handlers {
    public static Handler resource(String path, String contentType) {
        return (request, response) -> {
            response.addHeader("Content-Type", contentType);
            response.addHeader("Cache-Control", "public, max-age=604800, immutable");
            response.write(Objects.requireNonNull(Handlers.class.getResourceAsStream(path)));
        };
    }
    public static class Page {
        final String title;
        final Object[] body;
        public Page(String title, Object... body) {
            this.title = title;
            this.body = body;
        }
    }

    public static Handler page(BiFunction<Request, Response, Page> fun) {
        return ((request, response) -> {
            response.addHeader("Content-Type", "text/html; charset=utf-8");
            response.addHeader("Cache-Control", "no-cache");
            var page = fun.apply(request, response);
            response.write(
                    html(
                            head(
                                    title(page.title),
                                    tag("link").attr("rel", "stylesheet").attr("href", "/style.css"),
                                    tag("link").attr("rel", "icon").attr("type", "image/x-icon").attr("href", "https://api.chucknorris.io/img/favicon.ico")),
                            body(page.body)).toString());
        });
    }
}
