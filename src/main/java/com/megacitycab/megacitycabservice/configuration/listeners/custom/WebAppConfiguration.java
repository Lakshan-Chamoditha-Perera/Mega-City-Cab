package com.megacitycab.megacitycabservice.configuration.listeners.custom;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class WebAppConfiguration {
    private static final Logger logger = Logger.getLogger(WebAppConfiguration.class.getName());
    private static WebAppConfiguration instance;
    private final Properties properties;

    private WebAppConfiguration() {
        properties = new Properties();
        loadProperties();
    }

    public static WebAppConfiguration getInstance() {
        if (instance == null) {
            synchronized (WebAppConfiguration.class) {
                if (instance == null) {
                    instance = new WebAppConfiguration();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream baseInput = getClass().getClassLoader().getResourceAsStream("application.properties")) {

            if (baseInput == null) {
                throw new RuntimeException("Base properties file not found: application.properties");
            }

            properties.load(baseInput);

            String envType = properties.getProperty("APP_ENV", "dev").trim();
            String envSpecificFile = "application-" + envType + ".properties";

            try (InputStream envInput = getClass().getClassLoader().getResourceAsStream(envSpecificFile)) {
                if (envInput != null) {
                    properties.load(envInput);
                } else {
                    logger.info("Environment-specific properties file not found: " + envSpecificFile);
                }
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
