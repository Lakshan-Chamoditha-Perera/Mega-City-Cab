package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.repository.custom.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {


    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
