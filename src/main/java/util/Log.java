package util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Log {
    private final String className;
    public static boolean debug = false;
    public static boolean colorLogs = false;
    public Log(String className) {this.className = className;}
    public static Log get(Class<?> cls) {return new Log(cls.getName());}
    private String format(String level, String message, Object... args) {return Dates.currentTimeMillis() + " " + level + " " + className + " " + String.format(message, args);}
    public void debug(String message, Object... args) {if (debug) println(Console.Color.GREY, format("DEBUG", message, args));}
    public void info(String message, Object... args) {println(Console.Color.RESET, format("INFO", message, args));}
    public void warn(String message, Object... args) {println(Console.Color.YELLOW, format("WARN", message, args));}
    public void error(String message, Object... args) {println(Console.Color.RED, format("ERROR", message, args));}
    public void error(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        println(Console.Color.RED, format("ERROR", sw.toString()));
    }
    private void println(Console.Color color, Object message) {
        if (colorLogs)
            Console.println(color, message);
        else
            System.out.println(message);
    }
}
