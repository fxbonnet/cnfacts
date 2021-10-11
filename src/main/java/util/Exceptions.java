package util;

public class Exceptions {
    /**
     * Transforms a checked exception into an unchecked exception without any wrapping
     */
    public static <E extends Throwable> RuntimeException sneak(Throwable e) throws E {throw (E) e;}
}

