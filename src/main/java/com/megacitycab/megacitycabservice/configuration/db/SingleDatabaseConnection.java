package com.megacitycab.megacitycabservice.configuration.db;

import com.megacitycab.megacitycabservice.configuration.listeners.custom.WebAppConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleDatabaseConnection {
    private static SingleDatabaseConnection instance;
    private Connection connection;

    private SingleDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = WebAppConfiguration.getInstance().getProperty("DATABASE_URL");
        String user = WebAppConfiguration.getInstance().getProperty("DATABASE_USER");
        String password = WebAppConfiguration.getInstance().getProperty("DATABASE_PASSWORD");

        this.connection = DriverManager.getConnection(url, user, password);
    }

    public static SingleDatabaseConnection getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SingleDatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
