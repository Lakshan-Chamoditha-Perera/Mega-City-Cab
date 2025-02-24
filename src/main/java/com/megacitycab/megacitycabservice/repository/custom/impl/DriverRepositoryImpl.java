package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Driver;
import com.megacitycab.megacitycabservice.repository.custom.DriverRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverRepositoryImpl implements DriverRepository {

    @Override
    public Boolean save(Driver driver, Connection connection) throws SQLException {
        String sql = "INSERT INTO driver (firstName, lastName, licenseNumber, mobileNo, email) VALUES (?, ?, ?, ?, ?)";

        return SqlExecutor.execute(
                connection,
                sql,
                driver.getFirstName(),
                driver.getLastName(),
                driver.getLicenseNumber(),
                driver.getMobileNo(),
                driver.getEmail()
            );
    }

    @Override
    public List<Driver> findAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM driver WHERE deleted = false";

        ResultSet resultSet = SqlExecutor.execute(
                connection,
                sql
        );
        List<Driver> drivers = new ArrayList<>();

        while (resultSet.next()) {
            drivers.add(
                    Driver.builder()
                            .driverId(resultSet.getInt("driverId"))
                            .firstName(resultSet.getString("firstName"))
                            .lastName(resultSet.getString("lastName"))
                            .licenseNumber(resultSet.getString("licenseNumber"))
                            .mobileNo(resultSet.getString("mobileNo"))
                            .email(resultSet.getString("email"))
                            .availability(Boolean.valueOf(resultSet.getString("availability")))
//                            .createdAt(Date.valueOf(resultSet.getString("createdAt")))
//                            .updatedAt(Date.valueOf(resultSet.getString("updatedAt")))
//                            .isDeleted(Boolean.valueOf(resultSet.getString("isDeleted")))
//                            .addedUserId(resultSet.getInt("addedUserId"))
                            .build());
        }
        return drivers;
    }

    @Override
    public Driver findById(Integer id, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) throws SQLException {
        String sql = "UPDATE driver SET deleted = true WHERE driverId = ?";
        return SqlExecutor.execute(connection, sql, id);
    }

    @Override
    public Boolean updateById(Driver driver, Connection connection) throws SQLException {
        String sql = "UPDATE driver SET firstName = ?, lastName = ?, licenseNumber = ?, mobileNo = ?, email = ? WHERE driverId = ?";
        return SqlExecutor.execute(
                connection,
                sql,
                driver.getFirstName(),
                driver.getLastName(),
                driver.getLicenseNumber(),
                driver.getMobileNo(),
                driver.getEmail(),
                driver.getDriverId()
        );
    }

    @Override
    public List <Driver> getAllDriversNotAssignedVehicle(Connection connection) throws SQLException {
        String sql = "SELECT * FROM driver d LEFT JOIN vehicle v ON d.driverId = v.driverId WHERE v.driverId IS NULL";
        ResultSet resultSet = SqlExecutor.execute(
                connection,
                sql
        );
        List<Driver> drivers = new ArrayList<>();

        while (resultSet.next()) {
            drivers.add(
                    Driver.builder()
                            .driverId(resultSet.getInt("driverId"))
                            .firstName(resultSet.getString("firstName"))
                            .lastName(resultSet.getString("lastName"))
                            .licenseNumber(resultSet.getString("licenseNumber"))
                            .mobileNo(resultSet.getString("mobileNo"))
                            .email(resultSet.getString("email"))
                            .availability(Boolean.valueOf(resultSet.getString("availability")))
//                            .createdAt(Date.valueOf(resultSet.getString("createdAt")))
//                            .updatedAt(Date.valueOf(resultSet.getString("updatedAt")))
//                            .isDeleted(Boolean.valueOf(resultSet.getString("isDeleted")))
//                            .addedUserId(resultSet.getInt("addedUserId"))
                            .build());
        }
        return drivers;
    }

    @Override
    public Boolean updateDriverAvailability(Connection connection, boolean availability, Integer id) throws SQLException {
        String sql = "UPDATE driver SET availability = ? WHERE driverId = ?";
        return SqlExecutor.execute(
                connection,
                sql,
                availability,
                id
        );
    }

    @Override
    public Integer getDriverAssignedVehicleCount(Integer id, Connection connection) throws SQLException {
        String checkAssignedVehicleSql = "SELECT COUNT(*) FROM vehicle WHERE driverId = ?";
        return SqlExecutor.execute(connection, checkAssignedVehicleSql, id);
    }

}
