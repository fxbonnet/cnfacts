package inject;

import java.util.function.Function;
import java.util.function.Supplier;

public class Component<T> {
    public final Class<T> clazz;
    public final Function<ApplicationContext, T> constructor;
    public Component(Class<T> clazz, Function<ApplicationContext, T> constructor) {
        this.clazz = clazz;
        this.constructor = constructor;
    }
    public Component(Class<T> clazz, Supplier<T> supplier) {
        this.clazz = clazz;
        this.constructor = c -> supplier.get();
    }
}
