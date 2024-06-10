package utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {
    private static ObjectMapper mapper = new ObjectMapper();
    public static String returnJson(Object inputObject){
        try {
            return mapper.writeValueAsString(inputObject);
        }
        catch (Exception  e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T returnObject(String input,Class<T> clazz){
        try {
            return mapper.readValue(input, clazz);
        }
        catch (Exception  e) {
            e.printStackTrace();
        }
        return null;
    }
}