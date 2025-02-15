package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final Logger logger = Logger.getLogger(CustomerRepositoryImpl.class.getName());

    @Override
    public Customer save(Customer entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO customer (firstName, lastName, email, nic, address, mobileNo, dateOfBirth) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getNic());
            statement.setString(5, entity.getAddress());
            statement.setString(6, entity.getMobileNo());
            statement.setString(7, entity.getDateOfBirth().toString());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setCustomerId(generatedKeys.getInt(1));
                    }
                }
                return entity;
            }
            return null;
        }
    }

    @Override
    public List<Customer> findAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer WHERE isDeleted = false";
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(
                        Customer.builder()
                                .customerId(resultSet.getInt("customerId"))
                                .firstName(resultSet.getString("firstName"))
                                .lastName(resultSet.getString("lastName"))
                                .email(resultSet.getString("email"))
                                .nic(resultSet.getString("nic"))
                                .address(resultSet.getString("address"))
                                .mobileNo(resultSet.getString("mobileNo"))
                                .dateOfBirth(Date.valueOf(resultSet.getString("dateOfBirth")))
                                .build());
            }
        }
        return customers;
    }

    @Override
    public Customer findById(Integer id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId = ? AND isDeleted = false";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Customer.builder()
                            .customerId(resultSet.getInt("customerId"))
                            .firstName(resultSet.getString("firstName"))
                            .lastName(resultSet.getString("lastName"))
                            .email(resultSet.getString("email"))
                            .nic(resultSet.getString("nic"))
                            .address(resultSet.getString("address"))
                            .mobileNo(resultSet.getString("mobileNo"))
                            .dateOfBirth(Date.valueOf(resultSet.getString("dateOfBirth")))
                            .build();
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) throws SQLException {
        String sql = "UPDATE customer SET isDeleted = true WHERE customerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public Boolean updateById(Customer customer, Connection connection) throws SQLException {
        System.out.println("UPDATE REPO");
        String sql = "UPDATE customer SET firstName = ?, lastName = ?, email = ?, nic = ?, address = ?, mobileNo = ?, dateOfBirth = ? WHERE customerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getNic());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getMobileNo());
            statement.setString(7, customer.getDateOfBirth().toString());
            statement.setInt(8, customer.getCustomerId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
}