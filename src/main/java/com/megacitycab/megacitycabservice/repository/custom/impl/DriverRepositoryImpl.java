package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Driver;
import com.megacitycab.megacitycabservice.repository.custom.DriverRepository;

import java.sql.Connection;
import java.util.List;

public class DriverRepositoryImpl implements DriverRepository {


    @Override
    public Driver save(Driver entity, Connection connection) {
        return null;
    }

    @Override
    public List<Driver> findAll(Connection connection) {
        return List.of();
    }

    @Override
    public Driver findById(Integer id, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        return false;
    }
}
