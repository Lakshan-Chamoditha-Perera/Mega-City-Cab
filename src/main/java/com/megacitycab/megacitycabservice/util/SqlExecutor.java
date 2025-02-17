package com.megacitycab.megacitycabservice.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("unchecked")
public abstract class SqlExecutor {
    public static <T> T execute(Connection connection, String sql, Object... args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++)
            preparedStatement.setObject(i + 1, args[i]);
            return (sql.startsWith("SELECT") || sql.startsWith("select")) ?
                    (T) preparedStatement.executeQuery() :
                    (T) (Boolean) (preparedStatement.executeUpdate() > 0);
    }
}
