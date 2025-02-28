package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.dto.VehicleDTO;
import com.megacitycab.megacitycabservice.repository.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface QueryRepository extends Repository {
    List<VehicleDTO> getVehiclesByBookingId(Connection connection, Integer bookingId) throws SQLException;
}
