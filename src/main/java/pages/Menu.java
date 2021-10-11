package pages;

import api.ChuckApi;
import httpserver.Tag;
import inject.ApplicationContext;
import inject.Component;

import java.util.Arrays;

import static httpserver.Html.a;
import static httpserver.Html.div;
import static httpserver.Tag.tag;

public class Menu {
    public static final Component<Menu> COMPONENT = new Component<>(Menu.class, Menu::new);
    private final ChuckApi api;
    public Menu(ApplicationContext context) {api = context.get(ChuckApi.COMPONENT);}
    public Tag menu() {
        var categories = api.categories();
        return div().content(
                tag("fieldset").content(
                        tag("legend").content("Random facts"),
                        a().href("/").content("Show me another random fact!")),
                tag("fieldset").content(
                        tag("legend").content("Random facts by category"),
                        Arrays.stream(categories)
                                .map(category -> a().href("/?category=" + category).content(category))),
                tag("fieldset").content(
                        tag("legend").content("Search"),
                        tag("form").attr("action", "/search").content(
                                tag("input").attr("type", "text").attr("name", "query"),
                                tag("input").attr("type", "submit"))));
    }
}
