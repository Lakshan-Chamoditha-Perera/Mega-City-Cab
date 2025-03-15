package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.VehicleBookingDetails;
import com.megacitycab.megacitycabservice.repository.custom.VehicleBookingDetailsRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VehicleBookingDetailsRepositoryImpl implements VehicleBookingDetailsRepository {
    @Override
    public Boolean save(VehicleBookingDetails entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO vehicle_booking_details (bookingId,vehicleid) VALUES(?,?)";
        return SqlExecutor.execute(
                connection,
                sql,
                entity.getBookingId(),
                entity.getVehicleId()
        );
    }

    @Override
    public List<VehicleBookingDetails> findAll(Connection connection) throws SQLException {
        return List.of();
    }

    @Override
    public VehicleBookingDetails findById(Integer id, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public Integer getCount(Connection connection) throws SQLException {
        return 0;
    }


}
