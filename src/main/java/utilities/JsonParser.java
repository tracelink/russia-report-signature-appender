package utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParser {
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static String returnJson(Object inputObject) {
        try {
            return mapper.writeValueAsString(inputObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T returnObject(String input, Class<T> clazz) {
        try {
            return mapper.readValue(input, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T returnObject(String input, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(input, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}