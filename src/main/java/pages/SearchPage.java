package pages;

import api.ChuckApi;
import httpserver.Handlers;
import httpserver.Request;
import httpserver.Response;
import inject.ApplicationContext;
import inject.Component;

import java.util.Arrays;
import java.util.function.BiFunction;

import static httpserver.Html.div;
import static httpserver.Html.q;

public class SearchPage implements BiFunction<Request, Response, Handlers.Page> {
    public static final Component<SearchPage> COMPONENT = new Component<>(SearchPage.class, SearchPage::new);
    private final ChuckApi api;
    private final Menu menu;
    public SearchPage(ApplicationContext context) {
        api = context.get(ChuckApi.COMPONENT);
        menu = context.get(Menu.COMPONENT);
    }
    @Override public Handlers.Page apply(Request request, Response response) {
        var query = request.getQueryParameter("query");
        var facts = api.search(query);
        return new Handlers.Page(
                "Chuck Norris facts",
                menu.menu(),
                div().content(String.format("%s results", facts.total())),
                Arrays.stream(facts.result()).map(fact -> q().content(fact.value())));
    }
}
