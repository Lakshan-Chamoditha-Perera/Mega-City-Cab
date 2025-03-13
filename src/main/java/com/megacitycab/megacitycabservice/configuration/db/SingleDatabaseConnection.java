package com.megacitycab.megacitycabservice.configuration.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SingleDatabaseConnection {
    private static SingleDatabaseConnection instance;
    private Connection connection;

    private SingleDatabaseConnection() throws SQLException, NamingException {
        // Lookup the DataSource object from the JNDI context
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/megacityDataSource");
        this.connection = dataSource.getConnection();
    }

    public static SingleDatabaseConnection getInstance() throws SQLException, NamingException {
        if (instance == null) {
            instance = new SingleDatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
