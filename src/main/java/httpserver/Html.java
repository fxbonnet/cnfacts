package httpserver;

public class Html {
    public static Tag html(Object... contents) {return Tag.tag("html").content(contents);}
    public static Tag head(Object... contents) {return Tag.tag("head").content(contents);}
    public static Tag title(Object contents) {return Tag.tag("title").content(contents);}
    public static Tag body(Object... contents) {return Tag.tag("body").content(contents);}
    public static Tag div(Object... contents) {return Tag.tag("div").content(contents);}
    public static Tag q(Object... contents) {return Tag.tag("q").content(contents);}
    public static Tag table(Object... contents) {return Tag.tag("table").content(contents);}
    public static Tag tr(Object... contents) {return Tag.tag("tr").content(contents);}
    public static Tag th(Object contents) {return Tag.tag("th").content(contents);}
    public static Tag td() {return Tag.tag("td");}
    public static class ATag extends Tag {
        private ATag() {super("a");}
        public ATag href(String url) {
            attr("href", url);
            return this;
        }
        public ATag title(String url) {
            attr("title", url);
            return this;
        }
        public ATag target(String target) {
            attr("target", target);
            return this;
        }
    }
    public static ATag a() {return new ATag();}
}
