package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.DriverRepository;
import com.megacitycab.megacitycabservice.repository.custom.VehicleBookingDetailsRepository;
import com.megacitycab.megacitycabservice.repository.custom.VehicleRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.VehicleService;
import com.megacitycab.megacitycabservice.util.RegExPatterns;
import com.megacitycab.megacitycabservice.util.TransactionCallback;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class VehicleServiceImpl implements VehicleService {
    private final TransactionManager transactionManager;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final VehicleBookingDetailsRepository vehicleBookingDetailsRepository;

    public VehicleServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        vehicleRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.VEHICLE);
        driverRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.DRIVER);
        vehicleBookingDetailsRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.VEHICLE_BOOKING_DETAILS);
    }

    @Override
    public List<VehicleDTO> getAllVehiclesWithDriver() throws RuntimeException, MegaCityCabException {
        return transactionManager.doReadOnly(
                connection -> vehicleRepository.getVehiclesWithDriver(connection));
    }

    @Override
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws RuntimeException, MegaCityCabException {
        // Validate vehicle fields using RegEx patterns
        validateVehicleFields(vehicleDTO);

        // Check if a vehicle with the same license plate already exists
        Boolean isExists = transactionManager.doReadOnly(connection ->
                vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate(), connection));

        if (isExists) {
            throw new MegaCityCabException(ErrorMessage.VEHICLE_ALREADY_EXISTS_FOR_LICENSE_PLATE);
        }

        return transactionManager.doInTransaction(
                connection -> vehicleRepository.save(
                        Vehicle.builder()
                                .licensePlate(vehicleDTO.getLicensePlate())
                                .model(vehicleDTO.getModel())
                                .brand(vehicleDTO.getBrand())
                                .passengerCount(vehicleDTO.getPassengerCount())
                                .color(vehicleDTO.getColor())
                                .availability(vehicleDTO.getAvailability())
                                .driverId(vehicleDTO.getDriverId() == 0 ? null : vehicleDTO.getDriverId())
                                .addedUserId(vehicleDTO.getAddedUserId())
                                .pricePerKm(vehicleDTO.getPricePerKm())
                                .build(),
                        connection
                )
        );
    }

    private void validateVehicleFields(VehicleDTO vehicleDTO) throws MegaCityCabException {
        if (!Pattern.matches(RegExPatterns.LICENSE_PLATE, vehicleDTO.getLicensePlate())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_LICENSE_PLATE);
        }
        if (!Pattern.matches(RegExPatterns.MODEL, vehicleDTO.getModel())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_MODEL);
        }
        if (!Pattern.matches(RegExPatterns.BRAND, vehicleDTO.getBrand())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_BRAND);
        }
        if (!Pattern.matches(RegExPatterns.COLOR, vehicleDTO.getColor())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_COLOR);
        }
        if (!Pattern.matches(RegExPatterns.POSITIVE_INTEGER, vehicleDTO.getPassengerCount() + "")) {
            throw new MegaCityCabException(ErrorMessage.INVALID_PASSENGER_COUNT);
        }

        if (!Pattern.matches(RegExPatterns.POSITIVE_FLOAT, vehicleDTO.getPricePerKm() + "")) {
            throw new MegaCityCabException(ErrorMessage.INVALID_PRICE_PER_KM);
        }

    }

    @Override
    public boolean deleteVehicle(Integer id) throws RuntimeException, MegaCityCabException {

        Vehicle vehicle = transactionManager.doReadOnly(connection -> {
            return vehicleRepository.findById(id, connection);
        });

        if (vehicle == null) {
            throw new MegaCityCabException(ErrorMessage.VEHICLE_NOT_FOUND);
        }

        Boolean hasBookings = transactionManager.doReadOnly(new TransactionCallback<Boolean>() {
            @Override
            public Boolean execute(Connection connection) throws SQLException, MegaCityCabException {
                return vehicleRepository.hasPendingOrConfirmedBookings(id, connection);
            }
        });
        if (hasBookings) {
            throw new MegaCityCabException(ErrorMessage.VEHICLE_HAS_BEEN_BOOKED);
        }

        return transactionManager.doInTransaction(connection -> {
            Integer driverId = vehicle.getDriverId();

            if (driverId != null && driverId != 0) {
                driverRepository.updateDriverAvailability(connection, true, driverId);
            }

            boolean isDeleted = vehicleRepository.delete(id, connection);
            return isDeleted;
        });
    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws MegaCityCabException, RuntimeException {

        validateVehicleFields(vehicleDTO);

        Vehicle existingVehicle = transactionManager.doReadOnly(connection -> {
            return vehicleRepository.findById(vehicleDTO.getVehicleId(), connection);
        });

        if (existingVehicle == null) {
            throw new MegaCityCabException(ErrorMessage.VEHICLE_NOT_FOUND);
        }

        Boolean licensePlateAvailable = transactionManager.doReadOnly(connection -> {
            return vehicleRepository.existsByLicensePlateExceptId(
                    vehicleDTO.getLicensePlate(), vehicleDTO.getVehicleId(), connection);
        });

        if (licensePlateAvailable) {
            throw new MegaCityCabException(ErrorMessage.VEHICLE_ALREADY_EXISTS_FOR_LICENSE_PLATE);
        }

        existingVehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        existingVehicle.setModel(vehicleDTO.getModel());
        existingVehicle.setBrand(vehicleDTO.getBrand());
        existingVehicle.setPassengerCount(vehicleDTO.getPassengerCount());
        existingVehicle.setColor(vehicleDTO.getColor());
        existingVehicle.setAvailability(vehicleDTO.getAvailability());
        existingVehicle.setPricePerKm(vehicleDTO.getPricePerKm());

        existingVehicle.setDriverId(vehicleDTO.getDriverId() == 0 ? null : vehicleDTO.getDriverId());

        return transactionManager.doInTransaction(connection -> {
            boolean isUpdated = vehicleRepository.update(existingVehicle, connection);

            if (isUpdated && existingVehicle.getDriverId() != null) {
                boolean driverAvailabilityUpdated = driverRepository.updateDriverAvailability(connection, true, existingVehicle.getDriverId());
                return driverAvailabilityUpdated;
            }
            return isUpdated;
        });
    }

    @Override
    public Integer getVehiclesCount() throws RuntimeException, MegaCityCabException {
        return transactionManager.doReadOnly(
                connection -> vehicleRepository.getCount(connection)
        );
    }

}
