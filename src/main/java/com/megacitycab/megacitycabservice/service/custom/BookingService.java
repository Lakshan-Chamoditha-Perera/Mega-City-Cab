package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.custom.BookingDTO;
import com.megacitycab.megacitycabservice.dto.custom.BookingStatsDTO;
import com.megacitycab.megacitycabservice.dto.custom.RevenueReportDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.Service;

import java.sql.SQLException;
import java.util.List;

public interface BookingService extends Service {

    Boolean addBooking(BookingDTO booking) throws RuntimeException, MegaCityCabException;

    Integer getBookingsCount() throws RuntimeException, MegaCityCabException;

    List<BookingDTO> getBookingsWithCustomer() throws RuntimeException, MegaCityCabException;

    Float getTotalProfit() throws RuntimeException, MegaCityCabException;

    Boolean updateBookingStatus(int id, String status) throws RuntimeException, MegaCityCabException, SQLException;

    BookingStatsDTO getBookingStats() throws MegaCityCabException;

    List<RevenueReportDTO> getRevenueReport(String reportType) throws MegaCityCabException;

}
