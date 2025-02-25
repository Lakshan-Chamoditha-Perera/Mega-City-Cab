package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.BookingDTO;
import com.megacitycab.megacitycabservice.dto.VehicleBookingDetailsDTO;
import com.megacitycab.megacitycabservice.entity.custom.Booking;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import com.megacitycab.megacitycabservice.entity.custom.VehicleBookingDetails;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.BookingRepository;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;
import com.megacitycab.megacitycabservice.repository.custom.VehicleBookingDetailsRepository;
import com.megacitycab.megacitycabservice.repository.custom.VehicleRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.BookingService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.sql.Date;

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final TransactionManager transactionManager;
    private final VehicleBookingDetailsRepository vehicleBookingDetailRepository;

    public BookingServiceImpl(TransactionManager transactionManager) {
        bookingRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.BOOKING);
        vehicleRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.VEHICLE);
        customerRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.CUSTOMER);
        vehicleBookingDetailRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.VEHICLE_BOOKING_DETAILS);
        this.transactionManager = transactionManager;
    }

    @Override
    public Boolean addBooking(BookingDTO bookingDTO) throws MegaCityCabException, RuntimeException {

            Boolean isCustomerExists = transactionManager.doReadOnly(
                    connection -> customerRepository.existsById(bookingDTO.getCustomerId(), connection));
            if (!isCustomerExists) {
                throw new MegaCityCabException(ErrorMessage.CUSTOMER_NOT_FOUND);
            }

            float[] totalPrice = {0}; // Use an array to store the value, allowing mutation inside the lambda

            transactionManager.doReadOnly(connection -> {
                for (VehicleBookingDetailsDTO element : bookingDTO.getVehicleBookingDetailsDTOSList()) {
                    Integer vehicleId = element.getVehicleId();

                    Vehicle vehicle = vehicleRepository.findById(vehicleId, connection);
                    if (vehicle == null) {
                        throw new MegaCityCabException(ErrorMessage.VEHICLE_NOT_FOUND);
                    }

                    // Check if vehicle is available for booking
                    boolean isVehicleAvailable = vehicleRepository.findVehicleAvailabilityOnSpecificDate(connection, vehicleId, Date.valueOf(bookingDTO.getPickupTime().toLocalDate()));
                    if (isVehicleAvailable) {
                        throw new MegaCityCabException(ErrorMessage.VEHICLE_NOT_AVAILABLE_FOR_BOOKING);
                    }
                    totalPrice[0] += vehicle.getPricePerKm() * bookingDTO.getDistance(); // Update the total price
                }
                return true;
            });
            float calculatedTotalPrice = totalPrice[0];

            Booking booking = new Booking();
            booking.setCustomerId(bookingDTO.getCustomerId());
            booking.setPickupTime(bookingDTO.getPickupTime());
            booking.setDestination(bookingDTO.getDestination());
            booking.setPickupLocation(bookingDTO.getPickupLocation());
            booking.setStatus(bookingDTO.getStatus());
            booking.setDistance(bookingDTO.getDistance());
            booking.setFare(bookingDTO.getFare());
            booking.setDiscount(bookingDTO.getDiscount());
            booking.setTax(bookingDTO.getTax());
            booking.setTotalPrice(calculatedTotalPrice);

            Boolean doneInTransaction = transactionManager.doInTransaction(connection -> {
                int bookingId = bookingRepository.saveAndGetGeneratedBookingId(connection, booking);

                // Save vehicle booking details
                for (VehicleBookingDetailsDTO element : bookingDTO.getVehicleBookingDetailsDTOSList()) {
                    VehicleBookingDetails bookingDetail = new VehicleBookingDetails();
                    bookingDetail.setVehicleId(element.getVehicleId());
                    bookingDetail.setBookingId(bookingId);
                    vehicleBookingDetailRepository.save(bookingDetail, connection);
                }
                return true;
            });
            return doneInTransaction;
    }

    @Override
    public Integer getBookingsCount() throws RuntimeException, MegaCityCabException {
        return transactionManager.doReadOnly(
                connection -> bookingRepository.getCount(connection)
        );

    }
}