package util;

import java.util.Objects;

public class Formatter {
    public static String toString(Object obj) {return obj != null ? Objects.toString(obj) : "";}
}
