package main;

import inject.Component;
import util.Log;

public class Configuration {
    private static final Log log = Log.get(Configuration.class);
    public static final Component<Configuration> COMPONENT = new Component<>(Configuration.class, Configuration::new);
    public final boolean logToConsole;
    public final boolean colorLogs;
    public final int port;
    private Configuration() {
        logToConsole = getBoolean("logToConsole");
        colorLogs = getBoolean("colorLogs", true);
        port = getInt("configuration", 8877);
    }
    private String getString(String property) {
        var result = System.getenv().get(property);
        log.info(property + "=" + hide(result));
        return result;
    }
    private boolean getBoolean(String property) {
        var result = Boolean.parseBoolean(System.getenv().get(property));
        log.info(property + "=" + result);
        return result;
    }
    private boolean getBoolean(String property, boolean defaultValue) {
        var propertyValue = System.getenv().get(property);
        var result = propertyValue != null ? Boolean.parseBoolean(System.getenv().get(property)) : defaultValue;
        log.info(property + "=" + result);
        return result;
    }
    private int getInt(String property, int defaultValue) {
        var propertyValue = System.getenv().get(property);
        var result = propertyValue != null ? Integer.parseInt(propertyValue) : defaultValue;
        log.info(property + "=" + result);
        return result;
    }
    private String hide(String str) {return str == null ? "null" : "******";}
}
