package utilities;

import java.io.*;
import java.util.Properties;


public class ConfigReader {

    static FileReader reader;

    static Properties prop;

    public static String getConfigPropertyVal(String propertyName) {
        String reportConfigPath = "";
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            reportConfigPath = prop.getProperty(propertyName);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return reportConfigPath;
    }

    public static void setProperties(String fileName, String key, String value) {
        String propValue = null;
        try {
            reader = new FileReader("src//test//resources//" + fileName + ".properties");
            prop = new Properties();
            prop.load(reader);
            prop.setProperty(key, value);
            OutputStream fr = new FileOutputStream("src//test//resources//" + fileName + ".properties");
            prop.store(fr, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
