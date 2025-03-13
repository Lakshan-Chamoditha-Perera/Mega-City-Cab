package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.BookingDTO;
import com.megacitycab.megacitycabservice.dto.custom.VehicleBookingDetailsDTO;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.custom.BookingService;
import com.megacitycab.megacitycabservice.util.TransactionManager;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceImplTest {

    private static TransactionManager transactionManager;
    private static BookingService bookingService;
    private static Integer testBookingId;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        // Initialize transaction manager and service
        transactionManager = new TransactionManager(); // Ensure this connects to your test DB
        bookingService = new BookingServiceImpl(transactionManager);
    }

    @Test
    @Order(1)
    @DisplayName("Test Adding a New Booking")
    void testAddBooking() throws MegaCityCabException {
        // Create a BookingDTO with sample data
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setCustomerId(4); // Assuming customer with ID 1 exists
        bookingDTO.setPickupTime(LocalDateTime.now());
        bookingDTO.setDestination("Central Park");
        bookingDTO.setPickupLocation("Times Square");
        bookingDTO.setStatus("Pending");
        bookingDTO.setDistance(10.5f);
        bookingDTO.setFare(50.0f);
        bookingDTO.setDiscount(5.0f);
        bookingDTO.setTax(2.5f);
        bookingDTO.setAddedUserId(1);

        // Add vehicle booking details
        List<VehicleBookingDetailsDTO> vehicleBookingDetails = new ArrayList<>();
        VehicleBookingDetailsDTO vehicleDetail = new VehicleBookingDetailsDTO();
        vehicleDetail.setVehicleId(3); // Assuming vehicle with ID 1 exists
        vehicleBookingDetails.add(vehicleDetail);
        bookingDTO.setVehicleBookingDetailsDTOSList(vehicleBookingDetails);

        // Save the booking
        Boolean result = bookingService.addBooking(bookingDTO);
        assertTrue(result, "Booking should be added successfully");

        // Fetch the booking to get ID for further tests
        List<BookingDTO> bookings = bookingService.getBookingsWithCustomer();
        System.out.println(bookings);
        testBookingId = bookings.stream()
                .filter(booking -> booking.getCustomerId() == 4)
                .findFirst()
                .map(BookingDTO::getBookingId)
                .orElse(null);

        assertNotNull(testBookingId, "Saved booking should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Fetching Bookings with Customer Details")
    void testGetBookingsWithCustomer() throws MegaCityCabException {
        List<BookingDTO> bookings = bookingService.getBookingsWithCustomer();
        assertFalse(bookings.isEmpty(), "There should be at least one booking");
    }

    @Test
    @Order(3)
    @DisplayName("Test Getting Bookings Count")
    void testGetBookingsCount() throws MegaCityCabException {
        Integer count = bookingService.getBookingsCount();
        assertNotNull(count, "Booking count should not be null");
        assertTrue(count >= 0, "Booking count should be non-negative");
    }

    @Test
    @Order(4)
    @DisplayName("Test Adding Booking with Invalid Customer")
    void testAddBookingWithInvalidCustomer() {
        // Create a BookingDTO with an invalid customer ID
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setCustomerId(999); // Assuming customer with ID 999 does not exist
        bookingDTO.setPickupTime(LocalDateTime.now());
        bookingDTO.setDestination("Central Park");
        bookingDTO.setPickupLocation("Times Square");
        bookingDTO.setStatus("Pending");
        bookingDTO.setDistance(10.5f);
        bookingDTO.setFare(50.0f);
        bookingDTO.setDiscount(5.0f);
        bookingDTO.setTax(2.5f);

        // Add vehicle booking details
        List<VehicleBookingDetailsDTO> vehicleBookingDetails = new ArrayList<>();
        VehicleBookingDetailsDTO vehicleDetail = new VehicleBookingDetailsDTO();
        vehicleDetail.setVehicleId(1); // Assuming vehicle with ID 1 exists
        vehicleBookingDetails.add(vehicleDetail);
        bookingDTO.setVehicleBookingDetailsDTOSList(vehicleBookingDetails);

        // Verify that the exception is thrown
        MegaCityCabException exception = assertThrows(MegaCityCabException.class, () -> {
            bookingService.addBooking(bookingDTO);
        });
        assertEquals(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage(), exception.getMessage(), "Exception message should match");
    }

    @Test
    @Order(5)
    @DisplayName("Test Adding Booking with Unavailable Vehicle")
    void testAddBookingWithUnavailableVehicle() {
        // Create a BookingDTO with a vehicle that is unavailable
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setCustomerId(4); // Assuming customer with ID 1 exists
        bookingDTO.setPickupTime(LocalDateTime.now());
        bookingDTO.setDestination("Central Park");
        bookingDTO.setPickupLocation("Times Square");
        bookingDTO.setStatus("Pending");
        bookingDTO.setDistance(10.5f);
        bookingDTO.setFare(50.0f);
        bookingDTO.setDiscount(5.0f);
        bookingDTO.setTax(2.5f);
        bookingDTO.setAddedUserId(1);

        // Add vehicle booking details with an unavailable vehicle
        List<VehicleBookingDetailsDTO> vehicleBookingDetails = new ArrayList<>();
        VehicleBookingDetailsDTO vehicleDetail = new VehicleBookingDetailsDTO();
        vehicleDetail.setVehicleId(3); // Assuming vehicle with ID 2 is unavailable
        vehicleBookingDetails.add(vehicleDetail);
        bookingDTO.setVehicleBookingDetailsDTOSList(vehicleBookingDetails);

        // Verify that the exception is thrown
        MegaCityCabException exception = assertThrows(MegaCityCabException.class, () -> {
            bookingService.addBooking(bookingDTO);
        });
        assertEquals(ErrorMessage.VEHICLE_NOT_AVAILABLE_FOR_BOOKING.getMessage(), exception.getMessage(), "Exception message should match");
    }

}