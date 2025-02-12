package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import com.megacitycab.megacitycabservice.repository.custom.VehicleRepository;

import java.sql.Connection;
import java.util.List;


public class VehicleRepositoryImpl implements VehicleRepository {


    @Override
    public Vehicle save(Vehicle entity, Connection connection) {
        return null;
    }

    @Override
    public List<Vehicle> findAll(Connection connection) {
        return List.of();
    }

    @Override
    public Vehicle findById(Integer id, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        return false;
    }
}
