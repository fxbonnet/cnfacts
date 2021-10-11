package httpserver;

import util.Exceptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Response {
    public int status = 200;
    final List<Header> headers = new ArrayList<>();
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    public static class Header {
        final String name;
        final String value;
        private Header(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
    public void addHeader(String name, String value) {headers.add(new Header(name, value));}
    public void write(String s) {write(s.getBytes(StandardCharsets.UTF_8));}
    public void write(byte[] b) {try {out.write(b);} catch (IOException e) {throw Exceptions.sneak(e);}}
    public void write(InputStream in) {try {write(in.readAllBytes());} catch (IOException e) {throw Exceptions.sneak(e);}}
    @Override public String toString() {return out.toString(StandardCharsets.UTF_8);}
}
