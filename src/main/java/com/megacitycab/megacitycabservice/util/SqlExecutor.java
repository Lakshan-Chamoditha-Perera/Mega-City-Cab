package com.megacitycab.megacitycabservice.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("unchecked")
public abstract class SqlExecutor {

    /**
     * Executes a SQL query or update based on the provided SQL statement and arguments.
     *
     * @param connection The database connection.
     * @param sql        The SQL statement to execute.
     * @param args       The arguments to be set in the SQL statement.
     * @param <T>        The return type, either ResultSet (for SELECT) or Boolean (for UPDATE/INSERT/DELETE).
     * @return The result of the execution: ResultSet for SELECT queries, Boolean for updates.
     * @throws SQLException If a database access error occurs.
     */
    public static <T> T execute(Connection connection, String sql, Object... args) throws SQLException {
        String resolvedQuery = resolveQueryWithValues(sql, args);
        System.out.println(resolvedQuery);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setPreparedStatementParameters(preparedStatement, args);

        if (isSelectQuery(sql)) {
//            System.out.println("Executing SELECT query.");
            return (T) preparedStatement.executeQuery();
        } else {
//            System.out.println("Executing UPDATE/INSERT/DELETE query.");
            int rowsAffected = preparedStatement.executeUpdate();
            return (T) (Boolean) (rowsAffected > 0);
        }
    }

    /**
     * Sets the parameters of the PreparedStatement.
     *
     * @param preparedStatement The PreparedStatement to set parameters for.
     * @param args              The arguments to set.
     * @throws SQLException If a database access error occurs.
     */
    private static void setPreparedStatementParameters(PreparedStatement preparedStatement, Object[] args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
    }

    /**
     * Checks if the SQL statement is a SELECT query.
     *
     * @param sql The SQL statement to check.
     * @return True if the SQL is a SELECT query, false otherwise.
     */
    private static boolean isSelectQuery(String sql) {
        return sql.trim().toLowerCase().startsWith("select");
    }

    /**
     * Resolves the SQL query by substituting placeholders with actual values.
     * This is for logging purposes only and should not be used for execution.
     *
     * @param sql  The SQL query with placeholders.
     * @param args The arguments to substitute into the query.
     * @return The resolved SQL query as a string.
     */
    private static String resolveQueryWithValues(String sql, Object[] args) {
        if (args == null || args.length == 0) {
            return sql;
        }

        String resolvedQuery = sql;
        for (Object arg : args) {
            resolvedQuery = resolvedQuery.replaceFirst("\\?", formatArgumentForLogging(arg));
        }

        return resolvedQuery;
    }

    /**
     * Formats an argument for logging in the resolved query.
     *
     * @param arg The argument to format.
     * @return The formatted argument as a string.
     */
    private static String formatArgumentForLogging(Object arg) {
        if (arg == null) {
            return "NULL";
        } else if (arg instanceof String || arg instanceof Character) {
            return "'" + arg.toString() + "'";
        } else {
            return arg.toString();
        }
    }
}