package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.repository.custom.UserRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {


    @Override
    public Boolean save(User entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO user (username, passwordHash, email) VALUES (?, ?, ?)";

//        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            // Set the parameters for the insert statement
//            statement.setString(1, entity.getUsername());
//            statement.setString(2, entity.getPasswordHash());
//            statement.setString(3, entity.getEmail());
//            int affectedRows = statement.executeUpdate();
//
//            if (affectedRows > 0) {
//                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        entity.setUserId(generatedKeys.getInt(1));
//                    }
//                }
//            }
//        }

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
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
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
    public Optional<User> findByEmail(String email, Connection connection) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
        }
        return Optional.empty();
    }

}
