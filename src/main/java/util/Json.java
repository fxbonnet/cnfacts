package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class Json {
    private final static ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new SimpleModule());
    public static <T> T parse(String json, Class<T> aClass) {try {return mapper.readValue(json, aClass);} catch (JsonProcessingException e) {throw Exceptions.sneak(e);}}
    public static String serialize(Object obj) {try {return mapper.writeValueAsString(obj);} catch (JsonProcessingException e) {throw Exceptions.sneak(e);}}
}

