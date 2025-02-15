package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.repository.Repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerRepository extends Repository<Customer, Integer> {
    Boolean updateById(Customer customer, Connection connection) throws SQLException;
}
