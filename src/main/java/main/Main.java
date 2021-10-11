package main;

import httpserver.Handlers;
import httpserver.WebServer;
import inject.ApplicationContext;
import pages.FactPage;
import pages.SearchPage;
import util.Log;

public class Main {
    public static void main(String... args) {
        var context = new ApplicationContext();
        Log.debug = true;
        var configuration = context.get(Configuration.COMPONENT);
        Log.colorLogs = configuration.colorLogs;
        var webServer = context.get(WebServer.COMPONENT);
        webServer.addHandler("/style.css", Handlers.resource("/style.css", "text/css"));
        webServer.addHandler("/", Handlers.page(context.get(FactPage.COMPONENT)));
        webServer.addHandler("/search", Handlers.page(context.get(SearchPage.COMPONENT)));
        webServer.start();
    }
}
