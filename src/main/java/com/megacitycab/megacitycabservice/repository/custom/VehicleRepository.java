package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
    List<VehicleDTO> getVehiclesWithDriver(Connection connection) throws SQLException;

    Boolean updateVehicleAvailability(Connection connection, boolean availability) throws SQLException;

    Boolean update(Vehicle build, Connection connection) throws SQLException;

    Boolean findVehicleAvailabilityOnSpecificDate(Connection connection, Integer vehicleId, Date localDateTime) throws SQLException;

    Boolean existsById(Connection connection, Integer vehicleId) throws SQLException;

    Boolean existsByLicensePlateExceptId(String licensePlate, int vehicleId, Connection connection) throws SQLException;

    Boolean hasPendingOrConfirmedBookings(Integer id, Connection connection) throws SQLException;

    Boolean existsByLicensePlate(String licensePlate, Connection connection) throws SQLException;
}
