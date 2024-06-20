package utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.GenerateSignatureService;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;


public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static void writeToFile(String file,String content){
        Path filePath = Paths.get(file);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE)) {
            // Write the content to the file without adding a line break
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

    }

    public static String readFromFile(String file){
        Path filePath = Paths.get(file);
        try{
            //reading content from the file path provided
            byte[] fileContent = Files.readAllBytes(filePath);
            return new String(fileContent, StandardCharsets.UTF_8);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void emptyFile(String file){
        //emptying the file
        Path filePath = Paths.get(file);
        try {
            Files.write(filePath, Collections.emptyList());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
