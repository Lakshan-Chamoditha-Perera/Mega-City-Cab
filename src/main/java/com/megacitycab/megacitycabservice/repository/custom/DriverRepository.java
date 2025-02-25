package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.entity.custom.Driver;
import com.megacitycab.megacitycabservice.repository.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DriverRepository extends Repository<Driver, Integer> {

    Boolean updateById(Driver driver, Connection connection) throws SQLException;

    List <Driver> getAllDriversNotAssignedVehicle(Connection connection) throws SQLException;

    Boolean updateDriverAvailability(Connection connection, boolean availability,Integer id) throws SQLException;

    Integer getDriverAssignedVehicleCount(Integer id, Connection connection) throws SQLException;

    Boolean existsByEmail(String email, Connection connection) throws SQLException;

    Boolean existsByMobile(String mobileNo, Connection connection) throws SQLException;

    Boolean existsById(Integer id, Connection connection) throws SQLException;

    Boolean existsByEmailExceptId(String email, int driverId, Connection connection) throws SQLException;

    Boolean existsByMobileExceptId(String mobileNo, int driverId, Connection connection) throws SQLException;

}
