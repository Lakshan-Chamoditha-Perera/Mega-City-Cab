package com.megacitycab.megacitycabservice.service.factory;

import com.megacitycab.megacitycabservice.service.Service;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.impl.*;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.sql.SQLException;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T extends Service> T getService(ServiceType type) {

        try {
            TransactionManager transactionManager = new TransactionManager();

            Service service = switch (type) {
                case AUTH -> new AuthServiceImpl(transactionManager);
                case BOOKING -> new BookingServiceImpl(transactionManager);
                case CUSTOMER -> new CustomerServiceImpl(transactionManager);
                case DRIVER -> new DriverServiceImpl(transactionManager);
                case USER -> new UserServiceImpl(transactionManager);
                case VEHICLE -> new VehicleServiceImpl(transactionManager);
            };
            return (T) service;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

