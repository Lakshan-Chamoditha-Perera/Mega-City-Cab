-- Drop and Create Database
DROP DATABASE IF EXISTS megacity_test_db;
CREATE DATABASE megacity_test_db;
USE megacity_test_db;

-- User Table
CREATE TABLE user
(
    userId       INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    createdAt    DATETIME   DEFAULT CURRENT_TIMESTAMP,
    updatedAt    DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted    TINYINT(1) DEFAULT 0
);

-- Customer Table
CREATE TABLE customer
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
    createdAt   DATETIME   DEFAULT CURRENT_TIMESTAMP,
    updatedAt   DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted   TINYINT(1) DEFAULT 0,
    FOREIGN KEY (addedUserId) REFERENCES user (userId)
);

CREATE INDEX customer_addedUserId_idx ON customer (addedUserId);
CREATE INDEX idx_customer_isDeleted ON customer (isDeleted);

-- Driver Table
CREATE TABLE driver
(
    driverId      INT AUTO_INCREMENT PRIMARY KEY,
    firstName     VARCHAR(255) NOT NULL,
    lastName      VARCHAR(255) NOT NULL,
    licenseNumber VARCHAR(50)  NOT NULL UNIQUE,
    mobileNo      VARCHAR(15)  NOT NULL UNIQUE,
    email         VARCHAR(255) NOT NULL UNIQUE,
    availability  TINYINT(1) DEFAULT 1,
    createdAt     DATETIME   DEFAULT CURRENT_TIMESTAMP,
    updatedAt     DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted     TINYINT(1) DEFAULT 0,
    addedUserId   INT,
    FOREIGN KEY (addedUserId) REFERENCES user (userId)
);

CREATE INDEX driver_addedUserId_idx ON driver (addedUserId);

-- Vehicle Table
CREATE TABLE vehicle
(
    vehicleId      INT AUTO_INCREMENT PRIMARY KEY,
    licensePlate   VARCHAR(15)  NOT NULL UNIQUE,
    model          VARCHAR(255) NOT NULL,
    brand          VARCHAR(255) NOT NULL,
    passengerCount INT          NOT NULL CHECK (passengerCount > 0),
    color          VARCHAR(50)  NOT NULL,
    availability   TINYINT(1) DEFAULT 1,
    createdAt      DATETIME   DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted      TINYINT(1) DEFAULT 0,
    addedUserId    INT,
    driverId       INT,
    pricePerKm     FLOAT,
    FOREIGN KEY (addedUserId) REFERENCES user (userId),
    FOREIGN KEY (driverId) REFERENCES driver (driverId)
);

CREATE INDEX vehicle_addedUserId_idx ON vehicle (addedUserId);
CREATE INDEX vehicle_driverId_idx ON vehicle (driverId);

-- Booking Table
CREATE TABLE booking
(
    bookingId      INT AUTO_INCREMENT PRIMARY KEY,
    customerId     INT,
    pickupLocation VARCHAR(255)   NOT NULL,
    destination    VARCHAR(255)   NOT NULL,
    pickupTime     DATETIME       NOT NULL,
    status         ENUM ('pending', 'confirmed', 'canceled') DEFAULT 'pending',
    distance       DECIMAL(8, 2)  NOT NULL CHECK (distance >= 0),
    fare           DECIMAL(10, 2) NOT NULL CHECK (fare >= 0),
    discount       DECIMAL(10, 2)                            DEFAULT 0.00 CHECK (discount >= 0),
    tax            DECIMAL(10, 2)                            DEFAULT 0.00 CHECK (tax >= 0),
    createdAt      DATETIME                                  DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME                                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted      TINYINT(1)                                DEFAULT 0,
    addedUserId    INT,
    total          DECIMAL(10, 2) AS (fare + tax - discount) STORED,
    FOREIGN KEY (addedUserId) REFERENCES user (userId),
    FOREIGN KEY (customerId) REFERENCES customer (customerId)
);

CREATE INDEX booking_addedUserId_idx ON booking (addedUserId);
CREATE INDEX booking_customerId_idx ON booking (customerId);

-- Vehicle Booking Details Table
CREATE TABLE vehiclebookingdetails
(
    vehicleBookingDetailsId INT AUTO_INCREMENT PRIMARY KEY,
    bookingId               INT,
    vehicleId               INT,
    createdAt               DATETIME   DEFAULT CURRENT_TIMESTAMP,
    updatedAt               DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted               TINYINT(1) DEFAULT 0,
    FOREIGN KEY (bookingId) REFERENCES booking (bookingId),
    FOREIGN KEY (vehicleId) REFERENCES vehicle (vehicleId)
);

CREATE INDEX vbd_bookingId_idx ON vehiclebookingdetails (bookingId);
CREATE INDEX vbd_vehicleId_idx ON vehiclebookingdetails (vehicleId);
