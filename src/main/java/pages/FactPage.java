package pages;

import api.ChuckApi;
import httpserver.Handlers;
import httpserver.Request;
import httpserver.Response;
import inject.ApplicationContext;
import inject.Component;

import java.util.function.BiFunction;

import static httpserver.Html.q;

public class FactPage implements BiFunction<Request, Response, Handlers.Page> {
    public static final Component<FactPage> COMPONENT = new Component<>(FactPage.class, FactPage::new);
    private final ChuckApi api;
    private final Menu menu;
    public FactPage(ApplicationContext context) {
        api = context.get(ChuckApi.COMPONENT);
        menu = context.get(Menu.COMPONENT);
    }
    @Override public Handlers.Page apply(Request request, Response response) {
        var category = request.getQueryParameter("category");
        var fact = category != null ? api.randomFact(category) : api.randomFact();
        return new Handlers.Page(
                "Chuck Norris fact",
                menu.menu(),
                q().content(fact.value()));
    }
}
