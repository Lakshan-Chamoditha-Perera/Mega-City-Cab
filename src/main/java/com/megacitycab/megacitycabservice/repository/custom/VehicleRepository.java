package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.dto.VehicleDTO;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import com.megacitycab.megacitycabservice.repository.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface VehicleRepository extends Repository<Vehicle, Integer> {
    List<VehicleDTO> getVehiclesWithDriver(Connection connection) throws SQLException;

    Boolean updateVehicleAvailability(Connection connection, boolean availability) throws SQLException;

    Boolean update(Vehicle build, Connection connection) throws SQLException;

    Boolean findVehicleAvailabilityOnSpecificDate(Connection connection, Integer vehicleId, Date localDateTime) throws SQLException;

    Boolean existsById(Connection connection, Integer vehicleId) throws SQLException;
}
