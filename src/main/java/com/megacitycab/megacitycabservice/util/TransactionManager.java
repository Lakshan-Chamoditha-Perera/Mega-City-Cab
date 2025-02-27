package com.megacitycab.megacitycabservice.util;

import com.megacitycab.megacitycabservice.configuration.listeners.custom.DatabaseConnectionPool;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
    private final DatabaseConnectionPool connectionPool;

    public TransactionManager() {
        this.connectionPool = DatabaseConnectionPool.getInstance();
    }

    public <T> T doInTransaction(TransactionCallback<T> callback) throws RuntimeException, MegaCityCabException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            T result = callback.execute(connection);
            connection.commit();
            return result;
        } catch (SQLException | RuntimeException e) {
            // Handle rollback in case of failure
            if (connection != null) {
                try {
                    connection.rollback();
                    logger.log(Level.SEVERE, "Transaction rolled back due to an error: {0}", e.getMessage());
                } catch (SQLException rollbackEx) {
                    logger.log(Level.SEVERE, "Failed to rollback transaction: {0}", rollbackEx.getMessage());
                }
            }
            throw new RuntimeException(e);
        } catch (MegaCityCabException e) {
            throw e;
        } finally {
            // Ensure connection is closed and returned to the pool
            closeConnection(connection);
        }
    }

    public <T> T doReadOnly(TransactionCallback<T> callback) throws RuntimeException, MegaCityCabException {
        Connection connection = null;
        try {
            // Acquire connection from the pool
            connection = connectionPool.getConnection();

            // Execute callback (read-only logic)
            return callback.execute(connection);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Read-only transaction failed: {0}", e.getMessage());
            throw new RuntimeException(e);
        } catch (MegaCityCabException e) {
            throw e;
        } finally {
            // Ensure connection is closed and returned to the pool
            closeConnection(connection);
        }
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true); // Reset auto-commit
                }
                connection.close(); // Return connection to the pool
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to close connection: {0}", e.getMessage());
            }
        }
    }
}
