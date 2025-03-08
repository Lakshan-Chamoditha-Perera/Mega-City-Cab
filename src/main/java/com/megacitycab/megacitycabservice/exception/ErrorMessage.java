package com.megacitycab.megacitycabservice.exception;

public enum ErrorMessage {

    // User-related errors
    USER_NOT_FOUND("User not found."),

    // Customer-related errors
    CUSTOMER_NOT_FOUND("Customer not found."),
    CUSTOMER_ALREADY_EXISTS("Customer already exists."),
    CUSTOMER_ALREADY_EXISTS_FOR_EMAIL("Another customer already exists for this email. Please use another email."),

    // Vehicle-related errors
    VEHICLE_NOT_FOUND("Vehicle not found."),
    VEHICLE_ALREADY_EXISTS_FOR_LICENSE_PLATE("Vehicle already exists for the given license plate."),
    VEHICLE_NOT_AVAILABLE_FOR_BOOKING("Vehicle is not available for booking."),
    VEHICLE_HAS_BEEN_BOOKED("Vehicle has been booked."),
    VEHICLE_ALREADY_BOOKED("Vehicle is already booked for the selected time."),

    // Driver-related errors
    DRIVER_NOT_FOUND("Driver not found."),
    DRIVER_ALREADY_EXISTS("Driver already exists."),
    DRIVER_MOBILE_ALREADY_EXISTS("Driver mobile number already exists."),
    DRIVER_ASSIGNED_TO_VEHICLE("Driver is already assigned to a vehicle."),

    // Booking & Payment-related errors
    INVALID_BOOKING("Invalid booking details provided."),
    PAYMENT_FAILED("Payment processing failed."),

    // Authorization errors
    UNAUTHORIZED_ACCESS("Unauthorized access detected.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
