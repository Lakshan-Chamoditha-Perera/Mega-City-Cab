package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.dto.BookingDTO;
import com.megacitycab.megacitycabservice.entity.custom.Booking;
import com.megacitycab.megacitycabservice.repository.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookingRepository extends Repository<Booking, Integer> {
    int saveAndGetGeneratedBookingId(Connection connection, Booking booking) throws SQLException;
    List<BookingDTO> getBookingsWithCustomer(Connection connection) throws SQLException;
}
