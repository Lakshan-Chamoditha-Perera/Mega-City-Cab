package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final Logger logger = Logger.getLogger(CustomerRepositoryImpl.class.getName());

    @Override
    public Boolean save(Customer entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO customer (firstName, lastName, email, nic, address, mobileNo, dateOfBirth) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setString(1, entity.getFirstName());
//            statement.setString(2, entity.getLastName());
//            statement.setString(3, entity.getEmail());
//            statement.setString(4, entity.getNic());
//            statement.setString(5, entity.getAddress());
//            statement.setString(6, entity.getMobileNo());
//            statement.setString(7, entity.getDateOfBirth().toString());
//            int affectedRows = statement.executeUpdate();
//
//            if (affectedRows > 0) {
//                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        entity.setCustomerId(generatedKeys.getInt(1));
//                    }
//                }
//                return entity;
//            }
//            return null;
//        }

        return SqlExecutor.execute(
                connection,
                sql,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getNic(),
                entity.getAddress(),
                entity.getMobileNo(),
                entity.getDateOfBirth().toString()
        );
    }

    @Override
    public List<Customer> findAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer WHERE isDeleted = false";

        ResultSet resultSet = SqlExecutor.execute(
                connection,
                sql
        );
        List<Customer> customers = new ArrayList<>();

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
        return customers;
    }

    @Override
    public Customer findById(Integer id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId = ? AND isDeleted = false";

        ResultSet resultSet = SqlExecutor.execute(
                connection,
                sql,
                id
        );

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
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) throws SQLException {
        String sql = "UPDATE customer SET isDeleted = true WHERE customerId = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, id);
//            int affectedRows = statement.executeUpdate();
//            return affectedRows > 0;
//        }

        return SqlExecutor.execute(connection, sql, id);

    }

    @Override
    public Boolean updateById(Customer customer, Connection connection) throws SQLException {
        String sql = "UPDATE customer SET firstName = ?, lastName = ?, email = ?, nic = ?, address = ?, mobileNo = ?, dateOfBirth = ? WHERE customerId = ?";

        return SqlExecutor.execute(
                connection,
                sql,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getNic(),
                customer.getAddress(),
                customer.getMobileNo(),
                customer.getDateOfBirth().toString(),
                customer.getCustomerId()
        );
    }
}