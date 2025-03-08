package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import com.megacitycab.megacitycabservice.repository.custom.VehicleRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VehicleRepositoryImpl implements VehicleRepository {

    @Override
    public Boolean save(Vehicle entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO vehicle (licensePlate, model, brand, passengerCount, color, availability, driverId) VALUES (?,?,?,?,?,?,?)";

        return SqlExecutor.execute(
                connection,
                sql,
                entity.getLicensePlate(),
                entity.getModel(),
                entity.getBrand(),
                entity.getPassengerCount(),
                entity.getColor(),
                entity.getAvailability(),
                entity.getDriverId()
        );

    }

    @Override
    public List<Vehicle> findAll(Connection connection) {
        return List.of();
    }

    @Override
    public Vehicle findById(Integer id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE vehicleId = ?";
        Vehicle vehicle = null;
        ResultSet resultSet = SqlExecutor.execute(connection, sql, id);

        if (resultSet.next()) {
            vehicle = Vehicle.builder()
                    .vehicleId(resultSet.getInt("vehicleId"))
                    .licensePlate(resultSet.getString("licensePlate"))
                    .model(resultSet.getString("model"))
                    .brand(resultSet.getString("brand"))
                    .passengerCount(resultSet.getInt("passengerCount"))
                    .color(resultSet.getString("color"))
                    .availability(resultSet.getBoolean("availability"))
                    .createdAt(resultSet.getDate("createdAt"))
                    .driverId(
                            resultSet.getString("driverId") == null ? 0 : resultSet.getInt("driverId")
                    )
//                    .updatedAt(Date.valueOf(resultSet.getString("updatedAt")))
                    .pricePerKm(resultSet.getFloat(("pricePerKm")))
                    .build();
        }
        return vehicle;
    }

    @Override
    public boolean delete(Integer id, Connection connection) throws SQLException {
        String sql = "UPDATE vehicle SET deleted = true, driverId = null WHERE vehicleId = ?";
        return SqlExecutor.execute(connection, sql, id);
    }

    @Override
    public Integer getCount(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vehicle WHERE deleted = false";
        ResultSet resultSet = SqlExecutor.execute(connection, sql);
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public List<VehicleDTO> getVehiclesWithDriver(Connection connection) throws SQLException {
        String sql = "SELECT v.*, d.firstName, d.lastName, d.driverId FROM vehicle v LEFT JOIN driver d on v.driverId = d.driverId WHERE v.deleted = false ";
        ResultSet resultSet = SqlExecutor.execute(connection, sql);

        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        while (resultSet.next()) {
            VehicleDTO build = VehicleDTO.builder()
                    .vehicleId(resultSet.getInt("vehicleId"))
                    .licensePlate(resultSet.getString("licensePlate"))
                    .model(resultSet.getString("model"))
                    .brand(resultSet.getString("brand"))
                    .passengerCount(resultSet.getInt("passengerCount"))
                    .color(resultSet.getString("color"))
                    .availability(resultSet.getBoolean("availability"))
                    .createdAt(resultSet.getDate("createdAt"))
                    .pricePerKm(resultSet.getFloat(("pricePerKm")))
//                    .updatedAt(Date.valueOf(resultSet.getString("updatedAt")))
                    .build();

            int driverId = resultSet.getInt("driverId");
            if (driverId != 0) {
                build.setDriverId(driverId);
                build.setDriverName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
            }
            vehicleDTOList.add(build);
        }
        return vehicleDTOList;
    }

    @Override
    public Boolean updateVehicleAvailability(Connection connection, boolean availability) throws SQLException {
        String sql = "UPDATE vehicle SET availability = ? WHERE vehicleId = ?";
        return SqlExecutor.execute(connection, sql);
    }

    @Override
    public Boolean update(Vehicle vehicle, Connection connection) throws SQLException {
        String sql = "UPDATE vehicle SET licensePlate = ?, model = ?, brand = ?, passengerCount = ?, color = ?, driverId = ? WHERE vehicleId = ? ";
        return SqlExecutor.execute(
                connection,
                sql,
                vehicle.getLicensePlate(),
                vehicle.getModel(),
                vehicle.getBrand(),
                vehicle.getPassengerCount(),
                vehicle.getColor(),
                vehicle.getDriverId(),
                vehicle.getVehicleId()
        );
    }

    @Override
    public Boolean findVehicleAvailabilityOnSpecificDate(Connection connection, Integer vehicleId, Date date) throws SQLException {
        String sql = """
                    SELECT b.pickupTime, vbd.vehicleId
                                FROM vehiclebookingdetails vbd
                                         JOIN booking b ON vbd.bookingId = b.bookingId
                                WHERE vbd.vehicleId = ?
                                  AND b.status = 'confirmed'
                                  AND DATE(b.pickupTime) = DATE(?)
                """;
        ResultSet resultSet = SqlExecutor.execute(connection, sql, vehicleId, date);
        return resultSet.next();
    }

    @Override
    public Boolean existsById(Connection connection, Integer vehicleId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vehicle where vehicleId = ? AND isDeleted = false";
        ResultSet resultSet = SqlExecutor.execute(connection, sql);
        resultSet.next() ;
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean existsByLicensePlateExceptId(String licensePlate, int vehicleId, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vehicle WHERE licensePlate = ? AND vehicleId <> ?";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, licensePlate, vehicleId);
        resultSet.next() ;
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean hasPendingOrConfirmedBookings(Integer id, Connection connection) throws SQLException {
        String sql = """
                SELECT 1
                FROM vehiclebookingdetails vbd
                         LEFT JOIN booking b ON b.bookingId = vbd.bookingId
                WHERE vbd.vehicleId = ?
                  AND b.status IN ('pending', 'confirmed')
                LIMIT 1
            """;

         ResultSet resultSet = SqlExecutor.execute(connection, sql, id);
        return resultSet.next();
    }

    @Override
    public Boolean existsByLicensePlate(String licensePlate, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vehicle WHERE licensePlate = ?";
        ResultSet resultSet = SqlExecutor.execute(connection, sql, licensePlate);
        resultSet.next() ;
        return resultSet.getInt(1) != 0;
    }


}
