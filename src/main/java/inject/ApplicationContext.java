package inject;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<Component<?>, Object> components = new HashMap<>();
    public <T> T get(Component<T> component) {
        var t = (T) components.get(component);
        if (t == null) {
            t = component.constructor.apply(this);
            if (t == null) {
                throw new IllegalStateException("Component " + component + " creation failed");
            }
            put(component, t); // just in case
        }
        return t;
    }
    public <T> void put(Component<T> component, T instance) {components.put(component, instance);}
}
