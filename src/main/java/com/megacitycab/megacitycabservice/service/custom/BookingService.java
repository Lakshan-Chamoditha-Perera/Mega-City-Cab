package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.BookingDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.Service;

import java.util.List;

public interface BookingService extends Service {

    Boolean addBooking(BookingDTO booking) throws RuntimeException, MegaCityCabException;

    Integer getBookingsCount() throws RuntimeException, MegaCityCabException;

    List<BookingDTO> getBookingsWithCustomer()throws RuntimeException, MegaCityCabException;
}
