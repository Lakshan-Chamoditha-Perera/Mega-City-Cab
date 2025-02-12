package com.megacitycab.megacitycabservice.service.factory;

import com.megacitycab.megacitycabservice.service.Service;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.impl.*;

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
        Service service = switch (type) {
            case AUTH -> new AuthServiceImpl();
            case BOOKING -> new BookingServiceImpl();
            case CUSTOMER -> new CustomerServiceImpl();
            case DRIVER -> new DriverServiceImpl();
            case USER -> new UserServiceImpl();
            case VEHICLE -> new VehicleServiceImpl();
            default -> throw new IllegalArgumentException("Invalid repository type");
        };
        return (T) service;
    }
}

