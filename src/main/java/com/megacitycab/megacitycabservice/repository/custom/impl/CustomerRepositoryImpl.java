package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;

import java.sql.Connection;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {


    @Override
    public Customer save(Customer entity, Connection connection) {
        return null;
    }

    @Override
    public List<Customer> findAll(Connection connection) {
        return List.of();
    }

    @Override
    public Customer findById(Integer id, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        return false;
    }
}
