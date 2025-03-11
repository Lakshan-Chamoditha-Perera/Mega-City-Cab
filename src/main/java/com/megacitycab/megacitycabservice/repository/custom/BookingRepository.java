package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.dto.custom.BookingDTO;
import com.megacitycab.megacitycabservice.dto.custom.RevenueReportDTO;
import com.megacitycab.megacitycabservice.entity.custom.Booking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

    int saveAndGetGeneratedBookingId(Connection connection, Booking booking) throws SQLException;

    List<BookingDTO> getBookingsWithCustomer(Connection connection) throws SQLException;

    Float getTotalProfit(Connection connection) throws SQLException;

    Boolean existsBookingByBookingId(Connection connection, Integer bookingId) throws SQLException;

    Boolean updateBookingStatus(Connection connection, int id, String status) throws SQLException;

    int getBookingCountByStatus(Connection connection, String status) throws SQLException;

    Double getTotalRevenue(Connection connection) throws SQLException;

    List<RevenueReportDTO> getWeeklyRevenue(Connection connection) throws SQLException;

    List<RevenueReportDTO> getMonthlyRevenue(Connection connection) throws SQLException;

    List<RevenueReportDTO> getYearlyRevenue(Connection connection) throws SQLException;
}
