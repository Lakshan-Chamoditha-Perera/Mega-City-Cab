package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final Logger logger = Logger.getLogger(CustomerRepositoryImpl.class.getName());

    @Override
    public Boolean save(Customer entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO customer (firstName, lastName, email, nic, address, mobileNo, dateOfBirth, addedUserId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return SqlExecutor.execute(
                connection,
                sql,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getNic(),
                entity.getAddress(),
                entity.getMobileNo(),
                entity.getDateOfBirth().toString(),
                entity.getAddedUserId()
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
        return SqlExecutor.execute(connection, sql, id);
    }

    @Override
    public Integer getCount(Connection connection) throws SQLException {
        String sql = "{CALL sp_get_customer_count(?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.execute();
        return (Integer) callableStatement.getObject(1);
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

    @Override
    public Boolean existsById(Integer id, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer where customerId = ? AND isDeleted = false";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, id);
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsByEmail(String email, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer where email = ?";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, email);
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsByEmailExceptId(String email, int customerId, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE email = ? AND customerId <> ?";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, email, customerId);
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsByMobileNumber(String mobileNo, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer where mobileNo = ?";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, mobileNo);
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }
}