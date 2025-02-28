package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.dto.VehicleDTO;
import com.megacitycab.megacitycabservice.repository.custom.QueryRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryRepositoryImpl implements QueryRepository {

    @Override
    public List<VehicleDTO> getVehiclesByBookingId(Connection connection, Integer bookingId) throws SQLException {
        String sql = "SELECT * FROM vehicle v LEFT JOIN vehiclebookingdetails vbd ON v.vehicleId = vbd.vehicleId WHERE vbd.bookingId = ?";
        ResultSet resultSet = SqlExecutor.execute(
                connection,
                sql,
                bookingId
        );

        List<VehicleDTO> vehicleDTOList = new ArrayList<>();

        while (resultSet.next()) {
            vehicleDTOList.add(
                    VehicleDTO.builder()
                            .vehicleId(resultSet.getInt("vehicleId"))
                            .licensePlate(resultSet.getString("licensePlate"))
                            .model(resultSet.getString("model"))
                            .brand(resultSet.getString("brand"))
                            .color(resultSet.getString("color"))
                            .pricePerKm(resultSet.getInt("pricePerKm"))
                            .build()
            );
        }

        return vehicleDTOList;
    }
}
