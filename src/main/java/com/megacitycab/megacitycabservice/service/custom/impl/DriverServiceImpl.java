package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.DriverDTO;
import com.megacitycab.megacitycabservice.entity.custom.Driver;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.DriverRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.DriverService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.io.IOException;
import java.util.List;

public class DriverServiceImpl implements DriverService {
    private DriverRepository driverRepository;
    private final TransactionManager transactionManager;

    public DriverServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.driverRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.DRIVER);
    }

    @Override
    public List<DriverDTO> getAllAvailableDriversForVehicle() {
        try {
            List<Driver> driversList = transactionManager.doReadOnly(
                    connection -> driverRepository.getAllDriversNotAssignedVehicle(connection));

            return driversList.stream().map(
                            driver -> DriverDTO.builder()
                                    .driverId(driver.getDriverId())
                                    .firstName(driver.getFirstName())
                                    .lastName(driver.getLastName())
                                    .licenseNumber(driver.getLicenseNumber())
                                    .mobileNo(driver.getMobileNo())
                                    .availability(driver.getAvailability())
                                    .email(driver.getEmail())
                                    .build())
                    .toList();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean saveDriver(DriverDTO driverDTO) throws IOException {
        try {
            return transactionManager.doInTransaction(
                    connection -> driverRepository.save(
                            Driver.builder()
                                    .firstName(driverDTO.getFirstName())
                                    .lastName(driverDTO.getLastName())
                                    .licenseNumber(driverDTO.getLicenseNumber())
                                    .mobileNo(driverDTO.getMobileNo())
                                    .email(driverDTO.getEmail())
                                    .build(),
                            connection
                    ));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteDriver(Integer id) throws IOException {
        try {
            // Check if the driver is assigned to any vehicle
            Integer count = transactionManager.doReadOnly(connection -> {
                return driverRepository.getDriverAssignedVehicleCount(id, connection);
            });
            if (count > 0) {
                throw new IOException("Driver is assigned to a vehicle and cannot be deleted.");
            }

            // Proceed with the deletion
            return transactionManager.doInTransaction(connection -> {
                return driverRepository.delete(id, connection); // Perform the deletion
            });

        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting driver: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean updateDriver(DriverDTO driverDTO) {
        try {
            return transactionManager.doInTransaction(
                    connection -> driverRepository.updateById(
                            Driver.builder()
                                    .driverId(driverDTO.getDriverId())
                                    .firstName(driverDTO.getFirstName())
                                    .lastName(driverDTO.getLastName())
                                    .licenseNumber(driverDTO.getLicenseNumber())
                                    .mobileNo(driverDTO.getMobileNo())
                                    .email(driverDTO.getEmail())
                                    .build(),
                            connection
                    ));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        try {
            List<Driver> driversList = transactionManager.doReadOnly(
                    connection -> driverRepository.findAll(connection));

            return driversList.stream().map(
                            driver -> DriverDTO.builder()
                                    .driverId(driver.getDriverId())
                                    .firstName(driver.getFirstName())
                                    .lastName(driver.getLastName())
                                    .licenseNumber(driver.getLicenseNumber())
                                    .mobileNo(driver.getMobileNo())
                                    .availability(driver.getAvailability())
                                    .email(driver.getEmail())
                                    .build())
                    .toList();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
