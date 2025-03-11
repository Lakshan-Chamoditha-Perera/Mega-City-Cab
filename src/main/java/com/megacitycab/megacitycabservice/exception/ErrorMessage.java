package com.megacitycab.megacitycabservice.exception;

public enum ErrorMessage {

    // User-related errors
    USER_NOT_FOUND("The requested user could not be found. Please check the user ID and try again."),

    // Customer-related errors
    CUSTOMER_NOT_FOUND("The requested customer could not be found. Please verify the customer details and try again."),
    CUSTOMER_ALREADY_EXISTS("A customer with the provided details already exists in the system. Please use unique information."),
    CUSTOMER_ALREADY_EXISTS_FOR_EMAIL("The email address is already associated with another customer. Please use a different email address."),
    CUSTOMER_MOBILE_NUMBER_ALREADY_EXISTS("The mobile number is already registered to another customer. Please provide a unique mobile number."),

    // Customer validation errors
    INVALID_FIRST_NAME("The provided first name is invalid. Names should only contain letters and spaces."),
    INVALID_LAST_NAME("The provided last name is invalid. Names should only contain letters and spaces."),
    INVALID_ADDRESS("The provided address is invalid. Addresses should not contain special characters."),
    INVALID_NIC("The provided NIC is invalid. Please enter a valid Sri Lankan NIC number."),
    INVALID_DATE_OF_BIRTH("The provided date of birth is invalid. Please enter a valid date in the format YYYY-MM-DD."),
    INVALID_MOBILE_NUMBER("The provided mobile number is invalid. Please enter a valid mobile number."),
    INVALID_EMAIL("The provided email address is invalid. Please enter a valid email address."),

    // Vehicle-related errors
    VEHICLE_NOT_FOUND("The requested vehicle could not be found. Please verify the vehicle details and try again."),
    VEHICLE_ALREADY_EXISTS_FOR_LICENSE_PLATE("A vehicle with the provided license plate already exists. Please use a unique license plate."),
    VEHICLE_NOT_AVAILABLE_FOR_BOOKING("The selected vehicle is currently unavailable for booking. Please choose another vehicle."),
    VEHICLE_HAS_BEEN_BOOKED("The selected vehicle has already been booked. Please choose another vehicle or check back later."),
    VEHICLE_ALREADY_BOOKED("The vehicle is already booked for the selected time slot. Please choose a different time or vehicle."),

    // Driver-related errors
    DRIVER_NOT_FOUND("The requested driver could not be found. Please verify the driver details and try again."),
    DRIVER_ALREADY_EXISTS("A driver with the provided details already exists in the system. Please use unique information."),
    DRIVER_MOBILE_ALREADY_EXISTS("The mobile number is already registered to another driver. Please provide a unique mobile number."),
    DRIVER_ASSIGNED_TO_VEHICLE("The driver is already assigned to a vehicle. Please assign a different driver or remove the current assignment."),

    // Booking & Payment-related errors
    INVALID_BOOKING("The provided booking details are invalid. Please check the information and try again."),
    PAYMENT_FAILED("The payment could not be processed. Please check your payment details and try again."),
    BOOKING_NOT_FOUND("The requested booking could not be found. Please verify the booking ID and try again."),

    // General errors
    UNHANDLED_ERROR("An unexpected error occurred. Please contact support for assistance."),
    FEATURE_WILL_BE_AVAILABLE_SOON("This feature is currently under development and will be available soon. Thank you for your patience."),

    // Authorization errors
    UNAUTHORIZED_ACCESS("You do not have permission to access this resource. Please contact your administrator for assistance.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}