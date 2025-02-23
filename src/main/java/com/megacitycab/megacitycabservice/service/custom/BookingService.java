package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.BookingDTO;
import com.megacitycab.megacitycabservice.service.Service;

public interface BookingService extends Service {
    Boolean addBooking(BookingDTO booking);
}
