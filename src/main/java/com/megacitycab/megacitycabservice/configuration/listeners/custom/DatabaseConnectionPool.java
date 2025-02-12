package com.megacitycab.megacitycabservice.configuration.listeners.custom;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnectionPool {

    private static final Logger logger = Logger.getLogger(DatabaseConnectionPool.class.getName());
    private static DatabaseConnectionPool instance;
    private static DataSource dataSource;

    private DatabaseConnectionPool() {
        loadProperties();
    }

    public static DatabaseConnectionPool getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionPool.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * Get a database connection from the connection pool.
     *
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not initialized.");
        }
        return dataSource.getConnection();
    }

    public void loadProperties() {
        System.out.println("Initializing DatabaseConnectionPool...");
        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/DatabaseResource");
            logger.info("DatabaseConnectionPool initialized successfully.");
        } catch (NamingException e) {
            logger.severe("Failed to initialize DatabaseConnectionPool: " + e.getMessage());
            throw new RuntimeException("Error initializing JNDI DataSource", e);
        }
    }

}
