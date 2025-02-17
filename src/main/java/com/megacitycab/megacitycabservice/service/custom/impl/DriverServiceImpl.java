package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.CustomerDTO;
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
                                    .isAvailable(driver.isAvailable())
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
            return transactionManager.doInTransaction(
                    connection -> driverRepository.delete(id, connection));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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
}
