package com.megacitycab.megacitycabservice.repository.factory;

import com.megacitycab.megacitycabservice.configuration.listeners.custom.DatabaseConnectionPool;
import com.megacitycab.megacitycabservice.entity.Entity;
import com.megacitycab.megacitycabservice.repository.Repository;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.impl.*;

public class RepositoryFactory {

    private static RepositoryFactory instance;
    private final DatabaseConnectionPool databaseConnectionPool;

    private RepositoryFactory() {
        databaseConnectionPool = DatabaseConnectionPool.getInstance();
    }

    public static synchronized RepositoryFactory getInstance() {
        if (instance == null) {
            instance = new RepositoryFactory();
        }
        return instance;
    }


    @SuppressWarnings("unchecked")
    public <T extends Repository> T getRepository(RepositoryType type) {
        Repository repository = switch (type) {
            case BOOKING -> new BookingRepositoryImpl();
            case CUSTOMER -> new CustomerRepositoryImpl();
            case DRIVER -> new DriverRepositoryImpl();
            case USER -> new UserRepositoryImpl();
            case VEHICLE -> new VehicleRepositoryImpl();
            default -> throw new IllegalArgumentException("Invalid repository type");
        };
        return (T) repository;
    }

}
