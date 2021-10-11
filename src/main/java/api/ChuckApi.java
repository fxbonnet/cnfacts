package api;

import httpclient.Header;
import httpclient.Http;
import inject.ApplicationContext;
import inject.Component;
import util.Json;

/**
 * A simple client for chucknorris.io api based on jdk http client and jackson
 */
public class ChuckApi {
    public static final Component<ChuckApi> COMPONENT = new Component<>(ChuckApi.class, ChuckApi::new);
    public static record Fact(String url, String icon_url, String value, String[] categories) {}
    public static record SearchResult(int total, Fact[] result) {}
    private final Http http;
    public ChuckApi(ApplicationContext context) {http = context.get(Http.COMPONENT);}
    private String get(String resource, Header... headers) {return http.get("https://api.chucknorris.io/jokes" + resource, headers);}
    private String post(String resource, String payload, Header... headers) {return http.post("https://api.chucknorris.io/jokes" + resource, payload, headers);}
    public Fact randomFact() {return Json.parse(get("/random"), Fact.class);}
    public Fact randomFact(String category) {return Json.parse(get("/random?category=" + category), Fact.class);}
    public String[] categories() {return Json.parse(get("/categories"), String[].class);}
    public SearchResult search(String query) {return Json.parse(get("/search?query=" + query), SearchResult.class);}
}
