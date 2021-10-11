package util;

/**
 * https://stackoverflow.com/questions/33309136/change-color-in-os-x-console-output/33311700
 */
public class Console {
    private final static char ESCAPE = 0x1b;
    enum Color {
        RESET("0"), GREY("90"), YELLOW("33"), RED("31"), GREEN("32");
        final String code;
        Color(String code) {
            this.code = code;
        }
    }
    static void println(Color color, Object message) {System.out.println(ESCAPE + "[" + color.code + "m" + message);}
    public static void standard(Object message) {println(Color.RESET, message);}
    public static void green(Object message) {println(Color.GREEN, message);}
    public static void red(Object message) {println(Color.RED, message);}
}
