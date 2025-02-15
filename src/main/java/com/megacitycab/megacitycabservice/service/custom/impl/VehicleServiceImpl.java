package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.service.custom.VehicleService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

public class VehicleServiceImpl implements VehicleService {
    private final TransactionManager transactionManager;

    public VehicleServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
