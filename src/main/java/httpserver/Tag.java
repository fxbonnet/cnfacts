package httpserver;

import util.Formatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Tag {
    private final String name;
    private final Map<String, Object> attributes = new HashMap<>();
    private final List<Object> contents = new ArrayList<>();
    public static Tag tag(String name) {
        return new Tag(name);
    }
    public Tag(String name) {
        this.name = name;
    }
    public Tag attr(String name, Object value) {
        attributes.put(name, value);
        return this;
    }
    public Tag style(Object value) {
        attributes.put("style", value);
        return this;
    }
    public Tag styleClass(Object value) {
        attributes.put("class", value);
        return this;
    }
    public Tag id(Object value) {
        attributes.put("id", value);
        return this;
    }
    public Tag content(Object... contents) {
        for (var content : contents) {
            if (content instanceof Collection<?>)
                this.contents.addAll((Collection<?>) content);
            else if (content instanceof Stream<?>)
                ((Stream<?>) content).forEach(this.contents::add);
            else
                this.contents.add(content);
        }
        return this;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(name);
        for (var attr : attributes.entrySet())
            sb.append(" ").append(attr.getKey()).append("=\"").append(Formatter.toString(attr.getValue())).append("\"");
        if (contents.isEmpty())
            sb.append("/>");
        else {
            sb.append(">");
            for (var content : contents)
                sb.append(Formatter.toString(content));
            sb.append("</").append(name).append(">");
        }
        return sb.toString();
    }
}
