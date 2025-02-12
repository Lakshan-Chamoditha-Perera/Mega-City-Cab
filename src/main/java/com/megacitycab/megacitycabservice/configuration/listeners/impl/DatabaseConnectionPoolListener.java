package com.megacitycab.megacitycabservice.configuration.listeners.impl;

import com.megacitycab.megacitycabservice.configuration.listeners.custom.DatabaseConnectionPool;
import com.megacitycab.megacitycabservice.configuration.listeners.ConfigurationListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Logger;

@WebListener
public class DatabaseConnectionPoolListener implements ConfigurationListener {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionPoolListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing DatabaseConnectionPool...");
        DatabaseConnectionPool.getInstance(); // Initialize the singleton
        logger.info("DatabaseConnectionPool initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down DatabaseConnectionPool...");
        // Optional cleanup logic if needed
    }
}
