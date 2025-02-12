package com.megacitycab.megacitycabservice.configuration.listeners.impl;

import com.megacitycab.megacitycabservice.configuration.listeners.custom.WebAppConfiguration;
import com.megacitycab.megacitycabservice.configuration.listeners.ConfigurationListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Logger;

@WebListener
public class WebAppConfigurationListener implements ConfigurationListener {

    private static final Logger logger = Logger.getLogger(WebAppConfigurationListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing WebAppConfiguration...");
        WebAppConfiguration.getInstance(); // Initialize the singleton
        logger.info("WebAppConfiguration initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down WebAppConfiguration...");
        // Optional cleanup logic if needed
    }
}
