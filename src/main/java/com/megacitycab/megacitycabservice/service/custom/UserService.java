package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.entity.custom.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
}
