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
        try {
            // First, try to load from environment variables
            String databaseUrl = System.getenv("DATABASE_URL");
            String databaseUser = System.getenv("DATABASE_USER");
            String databasePassword = System.getenv("DATABASE_PASSWORD");

            if (databaseUrl != null && databaseUser != null && databasePassword != null) {
                properties.setProperty("DATABASE_URL", databaseUrl);
                properties.setProperty("DATABASE_USER", databaseUser);
                properties.setProperty("DATABASE_PASSWORD", databasePassword);
            } else {
                // Fallback to properties file if env variables are not set
                try (InputStream baseInput = getClass().getClassLoader().getResourceAsStream("application.properties")) {
                    if (baseInput == null) {
                        throw new RuntimeException("Base properties file not found: application.properties");
                    }
                    properties.load(baseInput);
                }
            }
        } catch (Exception e) {
            logger.warning("Error loading properties: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
