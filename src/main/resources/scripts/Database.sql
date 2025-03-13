-- Drop the database if it exists (use with caution)
DROP DATABASE IF EXISTS megacity_cab_db;

-- Create the Database
CREATE DATABASE IF NOT EXISTS megacity_cab_db;

USE megacity_cab_db;

-- User Table
CREATE TABLE User
(
    userId       INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    createdAt    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted    BOOLEAN  DEFAULT FALSE
);

-- Customer Table
CREATE TABLE Customer
(
    customerId  INT AUTO_INCREMENT PRIMARY KEY,
    addedUserId INT,
    firstName   VARCHAR(40)  NOT NULL,
    lastName    VARCHAR(40)  NOT NULL,
    address     VARCHAR(255) NOT NULL,
    nic         VARCHAR(20)  NOT NULL UNIQUE,
    dateOfBirth DATE         NOT NULL,
    mobileNo    VARCHAR(15)  NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    createdAt   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted   BOOLEAN  DEFAULT FALSE,
    FOREIGN KEY (addedUserId) REFERENCES User (userId)
);

-- Driver Table
CREATE TABLE Driver
(
    driverId      INT AUTO_INCREMENT PRIMARY KEY,
    firstName     VARCHAR(255) NOT NULL,
    lastName      VARCHAR(255) NOT NULL,
    licenseNumber VARCHAR(50)  NOT NULL UNIQUE,
    mobileNo      VARCHAR(15)  NOT NULL UNIQUE,
    email         VARCHAR(255) NOT NULL UNIQUE,
    availability  BOOLEAN  DEFAULT TRUE,
    createdAt     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       BOOLEAN  DEFAULT FALSE,
    addedUserId   INT,
    FOREIGN KEY (addedUserId) REFERENCES User (userId)
);

-- Vehicle Table
CREATE TABLE Vehicle
(
    vehicleId      INT AUTO_INCREMENT PRIMARY KEY,
    licensePlate   VARCHAR(15)  NOT NULL UNIQUE,
    model          VARCHAR(255) NOT NULL,
    brand          VARCHAR(255) NOT NULL,
    passengerCount INT          NOT NULL CHECK (passengerCount > 0),
    color          VARCHAR(50)  NOT NULL,
    availability   BOOLEAN  DEFAULT TRUE,
    createdAt      DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        BOOLEAN  DEFAULT FALSE,
    pricePerKm     FLOAT,
    addedUserId    INT,
    driverId       INT,
    FOREIGN KEY (addedUserId) REFERENCES User (userId),
    FOREIGN KEY (driverId) REFERENCES Driver (driverId)
);

-- Booking Table
CREATE TABLE Booking
(
    bookingId      INT AUTO_INCREMENT PRIMARY KEY,
    customerId     INT,
    pickupLocation VARCHAR(255)   NOT NULL,
    destination    VARCHAR(255)   NOT NULL,
    pickupTime     DATETIME       NOT NULL,
    status         ENUM ('pending', 'confirmed', 'canceled') DEFAULT 'pending',
    distance       DECIMAL(8, 2)  NOT NULL CHECK (distance >= 0),
    fare           DECIMAL(10, 2) NOT NULL CHECK (fare >= 0),
    discount       DECIMAL(10, 2)                            DEFAULT 0 CHECK (discount >= 0),
    tax            DECIMAL(10, 2)                            DEFAULT 0 CHECK (tax >= 0),
    createdAt      DATETIME                                  DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME                                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        BOOLEAN                                   DEFAULT FALSE,
    total          FLOAT,
    addedUserId    INT,
    FOREIGN KEY (addedUserId) REFERENCES User (userId),
    FOREIGN KEY (customerId) REFERENCES Customer (customerId)
);

-- Vehicle Booking Details Table
CREATE TABLE VehicleBookingDetails
(
    vehicleBookingDetailsId INT AUTO_INCREMENT PRIMARY KEY,
    bookingId               INT,
    vehicleId               INT,
    createdAt               DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted                 BOOLEAN  DEFAULT FALSE,
    FOREIGN KEY (bookingId) REFERENCES Booking (bookingId),
    FOREIGN KEY (vehicleId) REFERENCES Vehicle (vehicleId)
);

-- Indexes for optimization
CREATE INDEX idx_customer_isDeleted ON Customer (isDeleted);
CREATE INDEX idx_driver_mobileNo ON Driver (mobileNo);
CREATE INDEX idx_vehicle_licensePlate ON Vehicle (licensePlate);
CREATE INDEX idx_booking_status ON Booking (status);
CREATE INDEX idx_booking_pickupTime ON Booking (pickupTime);

-- Stored Procedures
DELIMITER $$

-- Procedure to get active drivers
CREATE PROCEDURE GetActiveDrivers()
BEGIN
    SELECT * FROM Driver WHERE availability = TRUE AND deleted = FALSE;
END $$

-- Procedure to get available vehicles
CREATE PROCEDURE GetAvailableVehicles()
BEGIN
    SELECT * FROM Vehicle WHERE availability = TRUE AND deleted = FALSE;
END $$

-- Procedure to get active customers
CREATE PROCEDURE GetActiveCustomers()
BEGIN
    SELECT * FROM Customer WHERE isDeleted = FALSE;
END $$

DELIMITER ;

-- Stored Procedure to Retrieve All Active Customers
DELIMITER //
CREATE PROCEDURE sp_get_all_customers()
BEGIN
    SELECT * FROM Customer WHERE isDeleted = FALSE;
END //
DELIMITER ;

-- Stored Procedure to Retrieve a Customer by ID
DELIMITER //
CREATE PROCEDURE sp_get_customer_by_id(IN p_customerId INT)
BEGIN
    SELECT * FROM Customer WHERE customerId = p_customerId AND isDeleted = FALSE;
END //
DELIMITER ;

-- Stored Procedure to Soft Delete a Customer
DELIMITER //
CREATE PROCEDURE sp_delete_customer(IN p_customerId INT)
BEGIN
    UPDATE Customer SET isDeleted = TRUE WHERE customerId = p_customerId;
END //
DELIMITER ;

-- Stored Procedure to Update Customer Details
DELIMITER //
CREATE PROCEDURE sp_update_customer(
    IN p_customerId INT,
    IN p_firstName VARCHAR(40),
    IN p_lastName VARCHAR(40),
    IN p_email VARCHAR(255),
    IN p_nic VARCHAR(20),
    IN p_address VARCHAR(255),
    IN p_mobileNo VARCHAR(15),
    IN p_dateOfBirth DATE
)
BEGIN
    UPDATE Customer
    SET firstName   = p_firstName,
        lastName    = p_lastName,
        email       = p_email,
        nic         = p_nic,
        address     = p_address,
        mobileNo    = p_mobileNo,
        dateOfBirth = p_dateOfBirth
    WHERE customerId = p_customerId
      AND isDeleted = FALSE;
END //
DELIMITER ;

-- Stored Procedure to Check If a Customer Exists by ID
DELIMITER //
CREATE PROCEDURE sp_exists_customer_by_id(IN p_customerId INT, OUT p_exists BOOLEAN)
BEGIN
    SELECT COUNT(*) > 0 INTO p_exists FROM Customer WHERE customerId = p_customerId AND isDeleted = FALSE;
END //
DELIMITER ;

-- Stored Procedure to Check If a Customer Exists by Email
DELIMITER //
CREATE PROCEDURE sp_exists_customer_by_email(IN p_email VARCHAR(255), OUT p_exists BOOLEAN)
BEGIN
    SELECT COUNT(*) > 0 INTO p_exists FROM Customer WHERE email = p_email;
END //
DELIMITER ;

-- Stored Procedure to Check If a Customer Exists by Email Except a Specific ID
DELIMITER //
CREATE PROCEDURE sp_exists_customer_by_email_except_id(
    IN p_email VARCHAR(255),
    IN p_customerId INT,
    OUT p_exists BOOLEAN
)
BEGIN
    SELECT COUNT(*) > 0 INTO p_exists FROM Customer WHERE email = p_email AND customerId <> p_customerId;
END //
DELIMITER ;


# Procedure for get count ---------------------------------------------------------------------------------------------
DELIMITER //
CREATE PROCEDURE sp_get_customer_count(OUT p_count INT)
BEGIN
    SELECT COUNT(*) INTO p_count FROM Customer WHERE isDeleted = FALSE;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_get_vehicle_count(OUT p_count INT)
BEGIN
    SELECT COUNT(*) INTO p_count FROM Vehicle WHERE deleted = FALSE;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_get_driver_count(OUT p_count INT)
BEGIN
    SELECT COUNT(*) INTO p_count FROM Driver WHERE deleted = FALSE;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_get_booking_count(OUT p_count INT)
BEGIN
    SELECT COUNT(*) INTO p_count FROM Booking WHERE deleted = FALSE;
END //

DELIMITER ;

# ----------------------------------------------------------------------------------------------------------------------
#  Stored Procedure for get total booking prices