package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.DriverDTO;
import com.megacitycab.megacitycabservice.entity.custom.Driver;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.DriverRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.DriverService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.util.List;

public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final TransactionManager transactionManager;

    public DriverServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.driverRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.DRIVER);
    }

    @Override
    public List<DriverDTO> getAllAvailableDriversForVehicle() throws RuntimeException, MegaCityCabException {
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

    }

    @Override
    public Boolean saveDriver(DriverDTO driverDTO) throws RuntimeException, MegaCityCabException {
        // Check if email already exists
        Boolean existsByEmail = transactionManager.doReadOnly(
                connection -> driverRepository.existsByEmail(driverDTO.getEmail(), connection));

        if (existsByEmail) {
            throw new MegaCityCabException(ErrorMessage.DRIVER_ALREADY_EXISTS);
        }

        // Check if mobile number already exists
        Boolean existsByMobile = transactionManager.doReadOnly(
                connection -> driverRepository.existsByMobile(driverDTO.getMobileNo(), connection));

        if (existsByMobile) {
            throw new MegaCityCabException(ErrorMessage.DRIVER_MOBILE_ALREADY_EXISTS);
        }

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
    }

    @Override
    public Boolean deleteDriver(Integer id) throws MegaCityCabException, RuntimeException {
        // Check if driver is assigned to any vehicle
        Integer assignedVehicleCount = transactionManager.doReadOnly(
                connection -> driverRepository.getDriverAssignedVehicleCount(id, connection));

        if (assignedVehicleCount > 0) {
            throw new MegaCityCabException(ErrorMessage.DRIVER_ASSIGNED_TO_VEHICLE);
        }

        // Check if driver exists before deleting
        Boolean existsById = transactionManager.doReadOnly(
                connection -> driverRepository.existsById(id, connection));

        if (!existsById) {
            throw new MegaCityCabException(ErrorMessage.DRIVER_NOT_FOUND);
        }

        return transactionManager.doInTransaction(
                connection -> driverRepository.delete(id, connection));
    }

    @Override
    public Boolean updateDriver(DriverDTO driverDTO) throws MegaCityCabException {
        // Check if driver exists
        Boolean existsById = transactionManager.doReadOnly(
                connection -> driverRepository.existsById(driverDTO.getDriverId(), connection));

        if (!existsById) {
            throw new MegaCityCabException(ErrorMessage.DRIVER_NOT_FOUND);
        }

        // Check if another driver has the same email
        Boolean emailExists = transactionManager.doReadOnly(
                connection -> driverRepository.existsByEmailExceptId(driverDTO.getEmail(), driverDTO.getDriverId(), connection));

        if (emailExists) {
            throw new MegaCityCabException(ErrorMessage.DRIVER_ALREADY_EXISTS);
        }

        // Check if another driver has the same mobile number
        Boolean mobileExists = transactionManager.doReadOnly(
                connection -> driverRepository.existsByMobileExceptId(driverDTO.getMobileNo(), driverDTO.getDriverId(), connection));

        if (mobileExists) {
            throw new MegaCityCabException(ErrorMessage.DRIVER_MOBILE_ALREADY_EXISTS);
        }

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
    }

    @Override
    public List<DriverDTO> getAllDrivers() throws RuntimeException, MegaCityCabException {

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

    }

    @Override
    public Integer getDriversCount() throws RuntimeException, MegaCityCabException {
        return transactionManager.doReadOnly(
                connection -> driverRepository.getCount(connection));
    }
}
