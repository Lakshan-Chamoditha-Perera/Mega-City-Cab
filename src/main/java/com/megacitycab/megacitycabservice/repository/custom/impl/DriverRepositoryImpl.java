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
    public Integer getCount(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM driver WHERE deleted = false";
        ResultSet resultSet = SqlExecutor.execute(connection, sql);
        return resultSet.next() ? resultSet.getInt(1) : 0;
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

        ResultSet resultSet = SqlExecutor.execute(connection, checkAssignedVehicleSql, id);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public Boolean existsByEmail(String email, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM driver WHERE email = ?";

        ResultSet resultSet = SqlExecutor.execute(connection, sql, email);
        resultSet.next() ;
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsByMobile(String mobileNo, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM driver WHERE mobileNo = ?";

        ResultSet resultSet = SqlExecutor.execute(connection, sql, mobileNo);
        resultSet.next() ;
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsById(Integer id, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM driver WHERE driverId = ?";

        ResultSet resultSet = SqlExecutor.execute(connection, sql, id);
        resultSet.next() ;
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsByEmailExceptId(String email, int driverId, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM driver WHERE email = ? AND driverId <> ?";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, email, driverId);
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsByMobileExceptId(String mobileNo, int driverId, Connection connection) throws SQLException{
        String sql = "SELECT COUNT(*) FROM driver WHERE mobileNo = ? AND driverId <> ?";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, mobileNo, driverId);
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }


}
