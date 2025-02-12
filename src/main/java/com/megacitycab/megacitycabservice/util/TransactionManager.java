package com.megacitycab.megacitycabservice.util;

import com.megacitycab.megacitycabservice.configuration.listeners.custom.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TransactionManager {
    private final DatabaseConnectionPool connectionPool;
    private final Logger logger = Logger.getLogger(TransactionManager.class.getName());

    public TransactionManager() {
        connectionPool = DatabaseConnectionPool.getInstance();
    }

    public <T> T doInTransaction(TransactionCallback<T> callback) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            T result = callback.execute(connection);

            connection.commit();
            return result;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace(); // Log the rollback error
                }
            }
            logger.warning(e.getMessage());
            throw new RuntimeException("Transaction failed", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Reset auto-commit to true
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log the close error
                }
            }
        }
    }

    public <T> T doReadOnly(TransactionCallback<T> callback) {
        Connection connection = null;
        try {
            // Get a connection from the pool
            connection = connectionPool.getConnection();
            // Execute the callback (read-only logic)
            return callback.execute(connection);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            throw new RuntimeException("Transaction failed", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log the close error
                }
            }
        }
    }

    @FunctionalInterface
    public interface TransactionCallback<T> {
        T execute(Connection connection) throws SQLException;
    }
}