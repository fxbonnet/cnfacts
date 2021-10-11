package httpserver;

public interface Handler {
    void handle(Request request, Response response);
}
