-- Drop the database if it exists (use with caution)
DROP DATABASE IF EXISTS megacity_test_db;

-- Create the Database
CREATE DATABASE IF NOT EXISTS megacity_test_db;

USE megacity_test_db;

-- user Table
CREATE TABLE user
(
    userId       INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    createdAt    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isDeleted    BOOLEAN  DEFAULT FALSE
);

-- customer Table
CREATE TABLE customer
(
    customerId  INT AUTO_INCREMENT PRIMARY KEY,
    addeduserId INT,
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
    FOREIGN KEY (addeduserId) REFERENCES user (userId)
);

-- driver Table
CREATE TABLE driver
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
    addeduserId   INT,
    FOREIGN KEY (addeduserId) REFERENCES user (userId)
);

-- vehicle Table
CREATE TABLE vehicle
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
    addeduserId    INT,
    driverId       INT,
    FOREIGN KEY (addeduserId) REFERENCES user (userId),
    FOREIGN KEY (driverId) REFERENCES driver (driverId)
);

-- booking Table
CREATE TABLE booking
(
    bookingId      INT AUTO_INCREMENT PRIMARY KEY,
    customerId     INT,
    pickupLocation VARCHAR(255)                     NOT NULL,
    destination    VARCHAR(255)                     NOT NULL,
    pickupTime     DATETIME                         NOT NULL,
    status         varchar(15)    default 'pending' null,
    distance       DECIMAL(8, 2)                    NOT NULL CHECK (distance >= 0),
    fare           DECIMAL(10, 2)                   NOT NULL CHECK (fare >= 0),
    discount       DECIMAL(10, 2) DEFAULT 0 CHECK (discount >= 0),
    tax            DECIMAL(10, 2) DEFAULT 0 CHECK (tax >= 0),
    createdAt      DATETIME       DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        BOOLEAN        DEFAULT FALSE,
    total          FLOAT,
    addeduserId    INT,
    FOREIGN KEY (addeduserId) REFERENCES user (userId),
    FOREIGN KEY (customerId) REFERENCES customer (customerId)
);

-- vehicle booking Details Table
CREATE TABLE vehicle_booking_details
(
    vehicleBookingDetailsId INT AUTO_INCREMENT PRIMARY KEY,
    bookingId               INT,
    vehicleId               INT,
    createdAt               DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted                 BOOLEAN  DEFAULT FALSE,
    FOREIGN KEY (bookingId) REFERENCES booking (bookingId),
    FOREIGN KEY (vehicleId) REFERENCES vehicle (vehicleId)
);

-- Indexes for optimization
CREATE INDEX idx_customer_isDeleted ON customer (isDeleted);
CREATE INDEX idx_driver_mobileNo ON driver (mobileNo);
CREATE INDEX idx_vehicle_licensePlate ON vehicle (licensePlate);
CREATE INDEX idx_booking_status ON booking (status);
CREATE INDEX idx_booking_pickupTime ON booking (pickupTime);

-- Stored Procedures
DELIMITER //
-- Procedure to get active drivers
CREATE PROCEDURE GetActiveDrivers()
BEGIN
SELECT * FROM driver WHERE availability = TRUE AND deleted = FALSE;
END //
DELIMITER ;

DELIMITER //
-- Procedure to get available vehicles
CREATE PROCEDURE GetAvailableVehicles()
BEGIN
SELECT * FROM vehicle WHERE availability = TRUE AND deleted = FALSE;
END //
DELIMITER ;

DELIMITER //

-- Procedure to get active customers
CREATE PROCEDURE GetActiveCustomers()
BEGIN
SELECT * FROM customer WHERE isDeleted = FALSE;
END //

DELIMITER ;

-- Stored Procedure to Retrieve All Active Customers
DELIMITER //
CREATE PROCEDURE sp_get_all_customers()
BEGIN
SELECT * FROM customer WHERE isDeleted = FALSE;
END //
DELIMITER ;

-- Stored Procedure to Retrieve a customer by ID
DELIMITER //
CREATE PROCEDURE sp_get_customer_by_id(IN p_customerId INT)
BEGIN
SELECT * FROM customer WHERE customerId = p_customerId AND isDeleted = FALSE;
END //
DELIMITER ;

-- Stored Procedure to Soft Delete a customer
DELIMITER //
CREATE PROCEDURE sp_delete_customer(IN p_customerId INT)
BEGIN
UPDATE customer SET isDeleted = TRUE WHERE customerId = p_customerId;
END //
DELIMITER ;

-- Stored Procedure to Update customer Details
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
UPDATE customer
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

-- Stored Procedure to Check If a customer Exists by ID
DELIMITER //
CREATE PROCEDURE sp_exists_customer_by_id(IN p_customerId INT, OUT p_exists BOOLEAN)
BEGIN
SELECT COUNT(*) > 0 INTO p_exists FROM customer WHERE customerId = p_customerId AND isDeleted = FALSE;
END //
DELIMITER ;

-- Stored Procedure to Check If a customer Exists by Email
DELIMITER //
CREATE PROCEDURE sp_exists_customer_by_email(IN p_email VARCHAR(255), OUT p_exists BOOLEAN)
BEGIN
SELECT COUNT(*) > 0 INTO p_exists FROM customer WHERE email = p_email;
END //
DELIMITER ;

-- Stored Procedure to Check If a customer Exists by Email Except a Specific ID
DELIMITER //
CREATE PROCEDURE sp_exists_customer_by_email_except_id(
    IN p_email VARCHAR(255),
    IN p_customerId INT,
    OUT p_exists BOOLEAN
)
BEGIN
SELECT COUNT(*) > 0 INTO p_exists FROM customer WHERE email = p_email AND customerId <> p_customerId;
END //
DELIMITER ;

-- Stored Procedure to Get customer Count
DELIMITER //
CREATE PROCEDURE sp_get_customer_count(OUT p_count INT)
BEGIN
SELECT COUNT(*) INTO p_count FROM customer WHERE isDeleted = FALSE;
END //
DELIMITER ;

-- Procedure for get count
DELIMITER //
CREATE PROCEDURE sp_get_vehicle_count(OUT p_count INT)
BEGIN
SELECT COUNT(*) INTO p_count FROM vehicle WHERE deleted = FALSE;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_get_driver_count(OUT p_count INT)
BEGIN
SELECT COUNT(*) INTO p_count FROM driver WHERE deleted = FALSE;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_get_booking_count(OUT p_count INT)
BEGIN
SELECT COUNT(*) INTO p_count FROM booking WHERE deleted = FALSE;
END //
DELIMITER ;

-- New Stored Procedures for booking Status Counts
DELIMITER //

CREATE PROCEDURE sp_get_bookings_count_by_status(
    IN p_status VARCHAR(15),
    OUT p_count INT
)
BEGIN
SELECT COUNT(*)
INTO p_count
FROM booking
WHERE status = p_status
  AND deleted = FALSE;
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_get_total_bookings_count(OUT p_count INT)
BEGIN
SELECT COUNT(*)
INTO p_count
FROM booking
WHERE deleted = FALSE;
END //
DELIMITER ;

# ----------------------------------------------------------------------------------------------------------------------
# Get Total revenue
DELIMITER //

CREATE PROCEDURE sp_get_total_revenue(
    OUT p_total_revenue DECIMAL(15, 2)
)
BEGIN
SELECT SUM(total)
INTO p_total_revenue
FROM booking
WHERE status = 'confirmed'
  AND deleted = FALSE;

IF p_total_revenue IS NULL THEN
        SET p_total_revenue = 0.00;
END IF;
END //

DELIMITER ;


#
DELIMITER //

CREATE PROCEDURE sp_get_weekly_revenue()
BEGIN
SELECT CONCAT(DATE_FORMAT(MIN(createdAt), '%Y-%m-%d'), ' to ', DATE_FORMAT(MAX(createdAt), '%Y-%m-%d')) AS period,
       SUM(total)                                                                                       AS totalRevenue,
       SUM(discount)                                                                                    AS totalDiscounts,
       SUM(tax)                                                                                         AS totalTaxes,
       SUM(total - discount + tax)                                                                      AS netRevenue
FROM booking
WHERE YEARWEEK(createdAt) = YEARWEEK(CURDATE())
  AND status = 'confirmed'
GROUP BY YEARWEEK(createdAt);
END //

DELIMITER ;

DELIMITER //


CREATE PROCEDURE sp_get_monthly_revenue()
BEGIN
SELECT DATE_FORMAT(MIN(createdAt), '%Y-%m') AS period, -- Use MIN() to resolve grouping issue
       SUM(total)                           AS totalRevenue,
       SUM(discount)                        AS totalDiscounts,
       SUM(tax)                             AS totalTaxes,
       SUM(total - discount + tax)          AS netRevenue
FROM booking
WHERE status = 'confirmed'
GROUP BY YEAR(createdAt), MONTH(createdAt);
END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE sp_get_yearly_revenue()
BEGIN
SELECT YEAR(createdAt)             AS period,
    SUM(total)                  AS totalRevenue,
    SUM(discount)               AS totalDiscounts,
    SUM(tax)                    AS totalTaxes,
    SUM(total - discount + tax) AS netRevenue
FROM booking
WHERE status = 'confirmed'
GROUP BY YEAR(createdAt);
END //

DELIMITER ;

# SAMPALE DATA ====================================
-- Insert into user table (3 users to add data)
INSERT INTO user (username, passwordHash, email)
VALUES ('admin_lk', 'hashed_pass_123', 'admin@megacity.lk'),
       ('user_colombo', 'hashed_pass_456', 'user1@megacity.lk'),
       ('manager_kandy', 'hashed_pass_789', 'manager@megacity.lk');
