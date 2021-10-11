package httpserver;

import com.sun.net.httpserver.HttpServer;
import inject.ApplicationContext;
import inject.Component;
import main.Configuration;
import util.Exceptions;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer implements Closeable {
    public static final Component<WebServer> COMPONENT = new Component<>(WebServer.class, WebServer::new);
    private final Configuration configuration;
    private final HttpServer server;
    public WebServer(ApplicationContext context) {
        configuration = context.get(Configuration.COMPONENT);
        var port = configuration.port;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 10);
        } catch (IOException e) {
            throw Exceptions.sneak(e);
        }
    }
    public void start() {server.start();}
    @Override public void close() {server.stop(1);} // delay 1 second
    public void addHandler(String path, Handler handler) {
        server.createContext(path, httpExchange -> {
            var request = new Request(httpExchange);
            var response = new Response();
            try {
                handler.handle(request, response);
            } catch (Exception e) {
                response = new Response();
                response.status = 500;
                response.addHeader("Content-Type", "text/plain; charset=utf-8");
                response.write(e.getClass().getSimpleName() + ": " + e.getMessage());
                e.printStackTrace();
            }
            response.headers.forEach(h -> httpExchange.getResponseHeaders().set(h.name, h.value));
            if ("HEAD".equals(httpExchange.getRequestMethod())) {
                // For head method we have to set the content-length header
                // manually and not use the response output stream
                httpExchange.getResponseHeaders().set("Content-length", Integer.toString(response.out.size()));
                httpExchange.sendResponseHeaders(response.status, -1);
            } else {
                httpExchange.sendResponseHeaders(response.status, response.out.size());
                try (OutputStream out = httpExchange.getResponseBody()) {
                    response.out.writeTo(out);
                }
            }
        });
    }
}
