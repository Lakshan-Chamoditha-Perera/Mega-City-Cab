package com.megacitycab.megacitycabservice.util;

import com.megacitycab.megacitycabservice.configuration.db.SingleDatabaseConnection;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
    private final SingleDatabaseConnection databaseConnection;

    public TransactionManager() throws SQLException, ClassNotFoundException {
        this.databaseConnection = SingleDatabaseConnection.getInstance();
    }

    public <T> T doInTransaction(TransactionCallback<T> callback) throws RuntimeException, MegaCityCabException {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
            connection.setAutoCommit(false);

            T result = callback.execute(connection);
            connection.commit();
            return result;
        } catch (SQLException | RuntimeException e) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resetConnection(connection);
        }
    }

    public <T> T doReadOnly(TransactionCallback<T> callback) throws RuntimeException, MegaCityCabException {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
            return callback.execute(connection);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Read-only transaction failed: {0}", e.getMessage());
            throw new RuntimeException(e);
        } catch (MegaCityCabException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resetConnection(connection);
        }
    }

    private void resetConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true); // Reset auto-commit
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to reset connection: {0}", e.getMessage());
            }
        }
    }
}
