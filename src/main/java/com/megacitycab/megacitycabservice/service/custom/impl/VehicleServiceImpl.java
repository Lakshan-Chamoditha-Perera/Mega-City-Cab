package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.VehicleDTO;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.DriverRepository;
import com.megacitycab.megacitycabservice.repository.custom.VehicleBookingDetailsRepository;
import com.megacitycab.megacitycabservice.repository.custom.VehicleRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.VehicleService;
import com.megacitycab.megacitycabservice.util.TransactionCallback;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

        Boolean isExists = transactionManager.doReadOnly(connection ->
                vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate(), connection));

        if (isExists) throw new MegaCityCabException(ErrorMessage.VEHICLE_ALREADY_EXISTS_FOR_LICENSE_PLATE);

        return transactionManager.doInTransaction(
                connection -> vehicleRepository.save(
                        Vehicle.builder()
                                .licensePlate(vehicleDTO.getLicensePlate())
                                .model(vehicleDTO.getModel())
                                .brand(vehicleDTO.getBrand())
                                .passengerCount(vehicleDTO.getPassengerCount())
                                .color(vehicleDTO.getColor())
                                .availability(vehicleDTO.getAvailability())
                                .driverId(vehicleDTO.getDriverId())
                                .build(),
                        connection
                )
        );
    }

    @Override
    public boolean deleteVehicle(Integer id) throws RuntimeException, MegaCityCabException {

            System.out.println("Starting deleteVehicle method for Vehicle ID: " + id);

            // Fetch the vehicle by ID
            Vehicle vehicle = transactionManager.doReadOnly(connection -> {
                System.out.println("Fetching vehicle from repository for ID: " + id);
                return vehicleRepository.findById(id, connection);
            });

            if (vehicle == null) {
                System.out.println("Vehicle with ID " + id + " not found.");
                throw new MegaCityCabException(ErrorMessage.VEHICLE_NOT_FOUND);
            }

            System.out.println("Vehicle found. Proceeding with deletion for Vehicle ID: " + id);

        //check vehicle has pending or confirmed booking
        Boolean hasBookings = transactionManager.doReadOnly(new TransactionCallback<Boolean>() { // Specify the return type
            @Override
            public Boolean execute(Connection connection) throws SQLException, MegaCityCabException {
                return vehicleRepository.hasPendingOrConfirmedBookings(id, connection);
            }
        });
        if (hasBookings) {
            throw new MegaCityCabException(ErrorMessage.VEHICLE_HAS_BEEN_BOOKED);
        }


        // Perform the deletion within a transaction
            return transactionManager.doInTransaction(connection -> {
                Integer driverId = vehicle.getDriverId();
                System.out.println("Driver ID associated with the vehicle: " + driverId);

                // If the vehicle has a driver, update the driver's availability
                if (driverId != null && driverId != 0) {
                    System.out.println("Updating driver availability for Driver ID: " + driverId);
                    driverRepository.updateDriverAvailability(connection, true, driverId);
                }

                // Delete the vehicle
                boolean isDeleted = vehicleRepository.delete(id, connection);
                System.out.println("Vehicle deletion status: " + isDeleted);

                return isDeleted;
            });
    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws MegaCityCabException, RuntimeException {
            System.out.println("Starting updateVehicle method for Vehicle ID: " + vehicleDTO.getVehicleId());

            // Find the existing vehicle by ID
            Vehicle existingVehicle = transactionManager.doReadOnly(connection -> {
                System.out.println("Fetching vehicle from repository for ID: " + vehicleDTO.getVehicleId());
                return vehicleRepository.findById(vehicleDTO.getVehicleId(), connection);
            });

            if (existingVehicle == null) {
                System.out.println("Vehicle with ID " + vehicleDTO.getVehicleId() + " not found.");
                throw new MegaCityCabException(ErrorMessage.VEHICLE_NOT_FOUND);
            }

            System.out.println("Vehicle found. Updating fields for Vehicle ID: " + vehicleDTO.getVehicleId());

        // exists vehicle by license plate except vehicleId
        Boolean licensePlateAvailable = transactionManager.doReadOnly(connection -> {
            return vehicleRepository.existsByLicensePlateExceptId(
                    vehicleDTO.getLicensePlate(), vehicleDTO.getVehicleId(), connection);
        });

        if (licensePlateAvailable) {
            throw new MegaCityCabException(ErrorMessage.VEHICLE_ALREADY_EXISTS_FOR_LICENSE_PLATE);
        }

            // Update fields with new values
            existingVehicle.setLicensePlate(vehicleDTO.getLicensePlate());
            existingVehicle.setModel(vehicleDTO.getModel());
            existingVehicle.setBrand(vehicleDTO.getBrand());
            existingVehicle.setPassengerCount(vehicleDTO.getPassengerCount());
            existingVehicle.setColor(vehicleDTO.getColor());
            existingVehicle.setAvailability(vehicleDTO.getAvailability());

            // Set driverId to null if it's 0, otherwise set it to the provided value
            existingVehicle.setDriverId(vehicleDTO.getDriverId() == 0 ? null : vehicleDTO.getDriverId());
            System.out.println("Driver ID set to: " + existingVehicle.getDriverId());

            // Perform the update within a transaction
            System.out.println("Starting transaction to update vehicle and driver availability.");
            return transactionManager.doInTransaction(connection -> {
                boolean isUpdated = vehicleRepository.update(existingVehicle, connection);
                System.out.println("Vehicle update status: " + isUpdated);

                if (isUpdated && existingVehicle.getDriverId() != null) {
                    System.out.println("Updating driver availability for Driver ID: " + existingVehicle.getDriverId());
                    boolean driverAvailabilityUpdated = driverRepository.updateDriverAvailability(connection, true, existingVehicle.getDriverId());
                    System.out.println("Driver availability update status: " + driverAvailabilityUpdated);
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
