package ru.geraskindenis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    private static final String propertiesFile = "application.properties";

    static {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Can't read the properties file \"" + propertiesFile + "\".", e);
        }
    }

    public void replacePlaceholder(String placeholder, String value) {
        if (value != null) {
            for (String key : PROPERTIES.stringPropertyNames()) {
                String replacedValue = PROPERTIES.getProperty(key).replace("${" + placeholder + "}", value);
                PROPERTIES.setProperty(key, replacedValue);
            }
        }
    }

    public static String getString(String property) {
        return PROPERTIES.getProperty(property);
    }

    public static int getInteger(String property) {
        return Integer.parseInt(PROPERTIES.getProperty(property));
    }
}
