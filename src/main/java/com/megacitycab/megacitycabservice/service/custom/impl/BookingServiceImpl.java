package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.service.custom.BookingService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

public class BookingServiceImpl implements BookingService {
    private final TransactionManager transactionManager;

    public BookingServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
