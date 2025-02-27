package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.dto.BookingDTO;
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
        String sql = "INSERT INTO booking (customerId, pickupTime, pickupLocation, destination, status, distance, discount, fare, total) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

        // Execute the update
        statement.executeUpdate();

        // Retrieve generated keys (the ID)
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1); // Return the generated booking ID
        } else {
            throw new SQLException("Failed to retrieve generated booking ID.");
        }
    }

    @Override
    public List<BookingDTO> getBookingsWithCustomer(Connection connection) throws SQLException {
        String sql = "SELECT b.bookingId, c.firstName, c.lastName, b.pickupTime, b.total, b.status " +
                "FROM booking b JOIN customer c ON b.customerId = c.customerId";
        List<BookingDTO> bookings = new ArrayList<>();

        try (ResultSet resultSet = SqlExecutor.execute(connection, sql)) {
            while (resultSet.next()) {
                // Create a BookingDTO object using the Builder pattern
                BookingDTO bookingDTO = BookingDTO.builder()
                        .bookingId(resultSet.getInt("bookingId"))
                        .customerName(resultSet.getString("firstName")+" "+resultSet.getString("lastName"))
                        .pickupTime(resultSet.getTimestamp("pickupTime").toLocalDateTime())
                        .total(resultSet.getFloat("total"))
                        .status(resultSet.getString("status"))
                        .build();

                // Add the BookingDTO to the list
                bookings.add(bookingDTO);
            }
        }

        return bookings;
    }
}



