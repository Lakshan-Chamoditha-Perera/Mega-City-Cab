package com.megacitycab.megacitycabservice.configuration.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleDatabaseConnection {
    private static SingleDatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/megacity_cab_db";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private SingleDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
