package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.*;
import com.megacitycab.megacitycabservice.entity.custom.Booking;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import com.megacitycab.megacitycabservice.entity.custom.VehicleBookingDetails;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.*;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.BookingService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final TransactionManager transactionManager;
    private final VehicleBookingDetailsRepository vehicleBookingDetailRepository;
    private final QueryRepository queryRepository;

    public BookingServiceImpl(TransactionManager transactionManager) {
        bookingRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.BOOKING);
        vehicleRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.VEHICLE);
        customerRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.CUSTOMER);
        vehicleBookingDetailRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.VEHICLE_BOOKING_DETAILS);
        queryRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.QUERY);
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
        booking.setAddedUserId(bookingDTO.getAddedUserId());

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

    @Override
    public List<BookingDTO> getBookingsWithCustomer() throws RuntimeException, MegaCityCabException {
        List<BookingDTO> bookingDTOS = transactionManager.doReadOnly(
                connection -> bookingRepository.getBookingsWithCustomer(connection)
        );

        for (int i = 0; i < bookingDTOS.size(); i++) {
            final BookingDTO bookingDTO = bookingDTOS.get(i);
            List<VehicleDTO> vehicleList = transactionManager.doReadOnly(
                    connection -> queryRepository.getVehiclesByBookingId(connection, bookingDTO.getBookingId())
            );
            bookingDTO.setVehicleList(vehicleList);
        }

        return bookingDTOS;
    }

    @Override
    public Float getTotalProfit() throws RuntimeException, MegaCityCabException {
        Float val = transactionManager.doReadOnly(
                connection -> bookingRepository.getTotalProfit(connection)
        );
        return 0f;
    }

    @Override
    public Boolean updateBookingStatus(int id, String status) throws RuntimeException, MegaCityCabException, SQLException {
        Boolean isExists = transactionManager.doReadOnly(
                connection -> {
                    return bookingRepository.existsBookingByBookingId(connection, id);
                }
        );

        if (!isExists) {
            throw new MegaCityCabException(ErrorMessage.BOOKING_NOT_FOUND);
        }
        return transactionManager.doInTransaction(connection -> {
            return bookingRepository.updateBookingStatus(connection, id, status);
        });
    }

    @Override
    public BookingStatsDTO getBookingStats() throws MegaCityCabException {
        try {
            return transactionManager.doReadOnly(connection -> {

                int pendingCount = bookingRepository.getBookingCountByStatus(connection, "pending");
                int confirmedCount = bookingRepository.getBookingCountByStatus(connection, "confirmed");
                int canceledCount = bookingRepository.getBookingCountByStatus(connection, "cancelled");
                double totalRevenue = bookingRepository.getTotalRevenue(connection);

//                int totalCount = bookingRepository.getBookingCountByStatus(connection,"total");

//                double pendingPercentage = totalCount > 0 ? (pendingCount * 100.0 / totalCount) : 0;
//                double confirmedPercentage = totalCount > 0 ? (confirmedCount * 100.0 / totalCount) : 0;
//                double canceledPercentage = totalCount > 0 ? (canceledCount * 100.0 / totalCount) : 0;

                BookingStatsDTO stats = new BookingStatsDTO();
                stats.setPendingBookings(pendingCount);
                stats.setConfirmedBookings(confirmedCount);
                stats.setCancelledBookings(canceledCount);
                stats.setTotalRevenue(totalRevenue);
//                stats.setTotalBookings(totalCount);

                return stats;
            });
        } catch (RuntimeException e) {
            throw new MegaCityCabException(ErrorMessage.UNHANDLED_ERROR, e);
        }
    }

    @Override
    public List<RevenueReportDTO> getRevenueReport(String reportType) throws MegaCityCabException {
        try {
            return transactionManager.doReadOnly(connection -> {
                switch (reportType.toUpperCase()) {
                    case "WEEKLY":
                        return bookingRepository.getWeeklyRevenue(connection);
                    case "MONTHLY":
                        return bookingRepository.getMonthlyRevenue(connection);
                    case "YEARLY":
                        return bookingRepository.getYearlyRevenue(connection);
                    default:
                        throw new MegaCityCabException(ErrorMessage.UNHANDLED_ERROR);
                }
            });
        } catch (RuntimeException e) {
            throw new MegaCityCabException(ErrorMessage.UNHANDLED_ERROR);
        }
    }


}