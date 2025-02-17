Drop database megacity_cab_db;

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
    isAvailable   BOOLEAN  DEFAULT TRUE,
    createdAt     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted     BOOLEAN  DEFAULT FALSE,
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
    isAvailable    BOOLEAN  DEFAULT TRUE,
    createdAt      DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted      BOOLEAN  DEFAULT FALSE,
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
    driverId       INT                                       DEFAULT NULL,
    vehicleId      INT                                       DEFAULT NULL,
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
    isDeleted      BOOLEAN                                   DEFAULT FALSE,
    addedUserId    INT,
    FOREIGN KEY (addedUserId) REFERENCES User (userId),
    FOREIGN KEY (customerId) REFERENCES Customer (customerId),
    FOREIGN KEY (driverId) REFERENCES Driver (driverId),
    FOREIGN KEY (vehicleId) REFERENCES Vehicle (vehicleId)
);
