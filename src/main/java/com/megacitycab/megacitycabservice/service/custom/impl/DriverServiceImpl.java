package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.service.custom.DriverService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

public class DriverServiceImpl implements DriverService {
    private final TransactionManager transactionManager;

    public DriverServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
