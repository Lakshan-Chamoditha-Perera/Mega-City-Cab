package com.megacitycab.megacitycabservice.repository.custom;


import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.repository.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {
    Optional<User> findByEmail(String email, Connection connection) throws SQLException;
}
