package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.repository.custom.UserRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {


    @Override
    public Boolean save(User entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO user (username, passwordHash, email) VALUES (?, ?, ?)";
        return SqlExecutor.execute(
                connection,
                sql,
                entity.getUsername(),
                entity.getPasswordHash(),
                entity.getEmail()
        );
    }


    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setUserId(resultSet.getInt("userId"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            users.add(user);
        }

        return users;
    }

    @Override
    public User findById(Integer id, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        return false;
    }

    @Override
    public Integer getCount(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE isDeleted = false";
        ResultSet resultSet = SqlExecutor.execute(connection, sql);
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public Optional<User> findByEmail(String email, Connection connection) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPasswordHash(resultSet.getString("passwordHash"));
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
