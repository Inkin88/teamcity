package ru.muzafarov.teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final static String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private final Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties();
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private void loadProperties() {
        try (InputStream stream = Config.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
            if (stream == null) {
                System.err.println("File not found" + CONFIG_PROPERTIES);
            }
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Error during file reading" + CONFIG_PROPERTIES);
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return getConfig().properties.getProperty(key);
    }
}
