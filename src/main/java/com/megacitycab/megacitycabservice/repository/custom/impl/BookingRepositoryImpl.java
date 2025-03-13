package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.BookingDTO;
import com.megacitycab.megacitycabservice.dto.custom.RevenueReportDTO;
import com.megacitycab.megacitycabservice.entity.custom.Booking;
import com.megacitycab.megacitycabservice.repository.custom.BookingRepository;
import com.megacitycab.megacitycabservice.util.SqlExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImpl implements BookingRepository {

    @Override
    public Boolean save(Booking entity, Connection connection) {
        return null;
    }

    @Override
    public List<Booking> findAll(Connection connection) {
        return List.of();
    }

    @Override
    public Booking findById(Integer id, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        return false;
    }

    @Override
    public Integer getCount(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM booking WHERE deleted = false";
        ResultSet resultSet = SqlExecutor.execute(connection, sql);
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public int saveAndGetGeneratedBookingId(Connection connection, Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (customerId, pickupTime, pickupLocation, destination, status, distance, discount, fare, total, addedUserId) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        // Set parameters from the booking object
        statement.setInt(1, booking.getCustomerId());
        statement.setTimestamp(2, Timestamp.valueOf(booking.getPickupTime())); // Assuming pickupTime is a LocalDateTime
        statement.setString(3, booking.getPickupLocation());
        statement.setString(4, booking.getDestination());
        statement.setString(5, booking.getStatus());
        statement.setDouble(6, booking.getDistance());
        statement.setDouble(7, booking.getDiscount());
        statement.setDouble(8, booking.getFare());
        statement.setDouble(9, booking.getTotalPrice());
        statement.setInt(10, booking.getAddedUserId());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1); // Return the generated booking ID
        } else {
            throw new SQLException("Failed to retrieve generated booking ID.");
        }
    }

    @Override
    public List<BookingDTO> getBookingsWithCustomer(Connection connection) throws SQLException {
        String sql = "SELECT b.bookingId, c.firstName, c.lastName, b.pickupTime, b.total, b.status, b.createdAt, b.distance,c.customerId " +
                "FROM booking b JOIN customer c ON b.customerId = c.customerId order by b.createdAt desc";
        List<BookingDTO> bookings = new ArrayList<>();

        try (ResultSet resultSet = SqlExecutor.execute(connection, sql)) {
            while (resultSet.next()) {
                BookingDTO bookingDTO = BookingDTO.builder()
                        .bookingId(resultSet.getInt("bookingId"))
                        .customerName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"))
                        .pickupTime(resultSet.getTimestamp("pickupTime").toLocalDateTime())
                        .createdAt(resultSet.getTimestamp("createdAt").toLocalDateTime())
                        .total(resultSet.getFloat("total"))
                        .status(resultSet.getString("status"))
                        .distance(resultSet.getDouble("distance"))
                        .customerId(resultSet.getInt("customerId"))
                        .build();
                bookings.add(bookingDTO);
            }
        }

        return bookings;
    }

    @Override
    public Float getTotalProfit(Connection connection) throws SQLException {
        String sql = "SELECT sum(profit) FROM booking WHERE deleted = false";
        return 0f;
    }

    @Override
    public Boolean existsBookingByBookingId(Connection connection, Integer bookingId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM booking WHERE bookingId = ?";

        ResultSet resultSet = SqlExecutor.execute(connection, sql, bookingId);
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Boolean updateBookingStatus(Connection connection, int id, String status) throws SQLException {
        String sql = "UPDATE booking SET status = ? WHERE bookingId = ?";
        return SqlExecutor.execute(connection, sql, status, id);
    }


    @Override
    public int getBookingCountByStatus(Connection connection, String status) throws SQLException {
        System.out.println(status);
        String sql = "{CALL sp_get_bookings_count_by_status(?, ?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, status);
        callableStatement.registerOutParameter(2, Types.INTEGER);
        callableStatement.execute();
        return callableStatement.getInt(2);
    }

    @Override
    public Double getTotalRevenue(Connection connection) throws SQLException {
        String sql = "{CALL sp_get_total_revenue(?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.registerOutParameter(1, Types.DECIMAL);
        callableStatement.execute();
        return callableStatement.getDouble(1);
    }

    @Override
    public List<RevenueReportDTO> getWeeklyRevenue(Connection connection) throws SQLException {
        String sql = "{CALL sp_get_weekly_revenue()}";
        return executeRevenueReportProcedure(connection, sql);
    }

    @Override
    public List<RevenueReportDTO> getMonthlyRevenue(Connection connection) throws SQLException {
        String sql = "{CALL sp_get_monthly_revenue()}";
        return executeRevenueReportProcedure(connection, sql);
    }

    @Override
    public List<RevenueReportDTO> getYearlyRevenue(Connection connection) throws SQLException {
        String sql = "{CALL sp_get_yearly_revenue()}";
        return executeRevenueReportProcedure(connection, sql);
    }

    private List<RevenueReportDTO> executeRevenueReportProcedure(Connection connection, String sql) throws SQLException {
        List<RevenueReportDTO> revenueReports = new ArrayList<>();
        CallableStatement statement = connection.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            RevenueReportDTO report = new RevenueReportDTO(
                    resultSet.getString("period"),
                    resultSet.getDouble("totalRevenue"),
                    resultSet.getDouble("totalDiscounts"),
                    resultSet.getDouble("totalTaxes"),
                    resultSet.getDouble("netRevenue")
            );
            revenueReports.add(report);
        }
        return revenueReports;
    }

}



