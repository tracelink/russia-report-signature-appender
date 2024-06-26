package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class StaticProperties {
    public static Properties properties;

    public static void loadProperties(String pathToFile) {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(pathToFile)) {
            properties.load(input);
            System.setProperty("log.level", properties.getProperty("log.level"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}