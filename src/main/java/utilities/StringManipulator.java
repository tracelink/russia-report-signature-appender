package utilities;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StringManipulator {

    public static String trimWhiteSpaces(String input){
        return input.trim().replaceAll("\\s+","");
    }

    public static String encodedBase64(String input){
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes,StandardCharsets.UTF_8);
    }
}

