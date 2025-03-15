-- Drop the database if it exists (use with caution)
DROP DATABASE IF EXISTS megacity_test_db;

-- Create the Database
CREATE DATABASE IF NOT EXISTS megacity_test_db;

USE megacity_test_db;

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
DELIMITER //
-- Procedure to get active drivers
CREATE PROCEDURE GetActiveDrivers()
BEGIN
    SELECT * FROM Driver WHERE availability = TRUE AND deleted = FALSE;
END //
DELIMITER ;

DELIMITER //
-- Procedure to get available vehicles
CREATE PROCEDURE GetAvailableVehicles()
BEGIN
    SELECT * FROM Vehicle WHERE availability = TRUE AND deleted = FALSE;
END //
DELIMITER ;

DELIMITER //

-- Procedure to get active customers
CREATE PROCEDURE GetActiveCustomers()
BEGIN
    SELECT * FROM Customer WHERE isDeleted = FALSE;
END //

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

-- Stored Procedure to Get Customer Count
DELIMITER //
CREATE PROCEDURE sp_get_customer_count(OUT p_count INT)
BEGIN
    SELECT COUNT(*) INTO p_count FROM Customer WHERE isDeleted = FALSE;
END //
DELIMITER ;

-- Procedure for get count
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

-- New Stored Procedures for Booking Status Counts
DELIMITER //

CREATE PROCEDURE sp_get_bookings_count_by_status(
    IN p_status VARCHAR(15),
    OUT p_count INT
)
BEGIN
    SELECT COUNT(*)
    INTO p_count
    FROM Booking
    WHERE status = p_status
      AND deleted = FALSE;
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_get_total_bookings_count(OUT p_count INT)
BEGIN
    SELECT COUNT(*)
    INTO p_count
    FROM Booking
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
    FROM Booking
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
-- Insert into User table (3 users to add data)
INSERT INTO User (username, passwordHash, email)
VALUES ('admin_lk', 'hashed_pass_123', 'admin@megacity.lk'),
       ('user_colombo', 'hashed_pass_456', 'user1@megacity.lk'),
       ('manager_kandy', 'hashed_pass_789', 'manager@megacity.lk');

-- Insert into Customer table (10 customers)
INSERT INTO Customer (addedUserId, firstName, lastName, address, nic, dateOfBirth, mobileNo, email)
VALUES (1, 'Nimal', 'Perera', '12 Galle Road, Colombo 03', '199012345V', '1990-05-12', '0771234567',
        'nimal.perera@email.lk'),
       (1, 'Kumari', 'Silva', '45 Kandy Road, Nugegoda', '198567890V', '1985-08-15', '0762345678',
        'kumari.silva@email.lk'),
       (2, 'Sunil', 'Fernando', '78 Main St, Galle', '199234567V', '1992-11-20', '0713456789', 'sunil.f@email.lk'),
       (2, 'Ayesha', 'Jayawardena', '23 Temple Rd, Kandy', '198912345V', '1989-03-10', '0784567890',
        'ayesha.j@email.lk'),
       (1, 'Ruwan', 'Gunasekera', '56 Sea View, Negombo', '199345678V', '1993-07-25', '0775678901', 'ruwan.g@email.lk'),
       (3, 'Dilani', 'Wickramasinghe', '89 Hill St, Matara', '198745678V', '1987-12-05', '0766789012',
        'dilani.w@email.lk'),
       (3, 'Chaminda', 'Rathnayake', '34 Lake Rd, Kurunegala', '199012678V', '1990-09-18', '0717890123',
        'chaminda.r@email.lk'),
       (2, 'Saman', 'De Silva', '67 Flower Rd, Colombo 07', '198834567V', '1988-02-14', '0788901234',
        'saman.ds@email.lk'),
       (1, 'Tharushi', 'Bandara', '15 Station Rd, Gampaha', '199456789V', '1994-06-30', '0779012345',
        'tharushi.b@email.lk'),
       (3, 'Lakmal', 'Weerasinghe', '92 Baseline Rd, Borella', '198678901V', '1986-10-22', '0760123456',
        'lakmal.w@email.lk');

-- Insert into Driver table (10 drivers)
INSERT INTO Driver (firstName, lastName, licenseNumber, mobileNo, email, availability, addedUserId)
VALUES ('Asanka', 'Jayasinghe', 'B1234567', '0771112233', 'asanka.j@drivers.lk', TRUE, 1),
       ('Priyantha', 'Kumara', 'B2345678', '0762223344', 'priyantha.k@drivers.lk', TRUE, 1),
       ('Nuwan', 'Senanayake', 'B3456789', '0713334455', 'nuwan.s@drivers.lk', FALSE, 1),
       ('Ranil', 'Pathirana', 'B4567890', '0784445566', 'ranil.p@drivers.lk', TRUE, 1),
       ('Dilshan', 'Mendis', 'B5678901', '0775556677', 'dilshan.m@drivers.lk', TRUE, 1),
       ('Suresh', 'Amarasinghe', 'B6789012', '0766667788', 'suresh.a@drivers.lk', FALSE, 1),
       ('Thilina', 'Herath', 'B7890123', '0717778899', 'thilina.h@drivers.lk', TRUE, 1),
       ('Malith', 'Wijesekara', 'B8901234', '0788889900', 'malith.w@drivers.lk', TRUE, 1),
       ('Kasun', 'Liyanage', 'B9012345', '0779990011', 'kasun.l@drivers.lk', FALSE, 1),
       ('Chathura', 'Ekanayake', 'B0123456', '0760001122', 'chathura.e@drivers.lk', TRUE, 1);

-- Insert into Vehicle table (10 vehicles)
INSERT INTO Vehicle (licensePlate, model, brand, passengerCount, color, availability, pricePerKm, addedUserId, driverId)
VALUES ('CAA-1234', 'Alto', 'Suzuki', 4, 'White', TRUE, 50.0, 1, 1),
       ('CAB-5678', 'Prius', 'Toyota', 4, 'Silver', TRUE, 75.0, 2, 2),
       ('CAC-9012', 'Perodua', 'Axia', 4, 'Red', FALSE, 60.0, 1, 3),
       ('CAD-3456', 'Fit', 'Honda', 5, 'Blue', TRUE, 70.0, 3, 4),
       ('CAE-7890', 'Wagon R', 'Suzuki', 4, 'Black', TRUE, 55.0, 2, 5),
       ('CAF-1234', 'Aqua', 'Toyota', 4, 'Green', FALSE, 80.0, 1, 6),
       ('CAG-5678', 'Vitz', 'Toyota', 4, 'Yellow', TRUE, 65.0, 3, 7),
       ('CAH-9012', 'Civic', 'Honda', 5, 'Grey', TRUE, 85.0, 2, 8),
       ('CAI-3456', 'Swift', 'Suzuki', 4, 'Orange', FALSE, 60.0, 1, 9),
       ('CAJ-7890', 'Corolla', 'Toyota', 5, 'White', TRUE, 90.0, 3, 10);

-- Insert into Booking table (10 bookings)
INSERT INTO Booking (customerId, pickupLocation, destination, pickupTime, status, distance, fare, discount, tax, total,
                     addedUserId)
VALUES (1, 'Colombo 03', 'Bandaranaike Intl Airport', '2025-03-01 07:00:00', 'confirmed', 30.0, 1500.00, 100.00, 50.00,
        1450.00, 1),
       (2, 'Nugegoda', 'Colombo Fort', '2025-03-02 09:30:00', 'pending', 10.0, 500.00, 0.00, 25.00, 525.00, 2),
       (3, 'Galle', 'Hikkaduwa', '2025-03-03 14:00:00', 'confirmed', 20.0, 1000.00, 50.00, 30.00, 980.00, 1),
       (4, 'Kandy', 'Peradeniya', '2025-03-04 16:30:00', 'canceled', 8.0, 400.00, 0.00, 20.00, 420.00, 3),
       (5, 'Negombo', 'Katunayake', '2025-03-05 08:00:00', 'confirmed', 15.0, 750.00, 50.00, 25.00, 725.00, 2),
       (6, 'Matara', 'Weligama', '2025-03-06 11:00:00', 'pending', 12.0, 600.00, 0.00, 30.00, 630.00, 1),
       (7, 'Kurunegala', 'Dambulla', '2025-03-07 13:00:00', 'confirmed', 25.0, 1250.00, 100.00, 40.00, 1190.00, 3),
       (8, 'Colombo 07', 'Bambalapitiya', '2025-03-08 15:00:00', 'confirmed', 5.0, 250.00, 0.00, 15.00, 265.00, 2),
       (9, 'Gampaha', 'Ja-Ela', '2025-03-09 10:00:00', 'canceled', 10.0, 500.00, 50.00, 25.00, 475.00, 1),
       (10, 'Borella', 'Maradana', '2025-03-10 17:00:00', 'confirmed', 7.0, 350.00, 0.00, 20.00, 370.00, 3);

-- Insert into VehicleBookingDetails table (10 records to match bookings)
INSERT INTO VehicleBookingDetails (bookingId, vehicleId)
VALUES (1, 1), -- Nimal to Airport with CAA-1234
       (2, 2), -- Kumari to Colombo Fort with CAB-5678
       (3, 3), -- Sunil to Hikkaduwa with CAC-9012
       (4, 4), -- Ayesha to Peradeniya with CAD-3456
       (5, 5), -- Ruwan to Katunayake with CAE-7890
       (6, 6), -- Dilani to Weligama with CAF-1234
       (7, 7), -- Chaminda to Dambulla with CAG-5678
       (8, 8), -- Saman to Bambalapitiya with CAH-9012
       (9, 9), -- Tharushi to Ja-Ela with CAI-3456
       (10, 10);
-- Lakmal to Maradana with CAJ-7890

-- Insert into Booking table (100 bookings)
INSERT INTO Booking (customerId, pickupLocation, destination, pickupTime, status, distance, fare, discount, tax, total,
                     addedUserId)
VALUES
-- Bookings for customerId 1
(1, 'Colombo 03', 'Bandaranaike Intl Airport', '2025-03-01 07:00:00', 'confirmed', 30.0, 1500.00, 100.00, 50.00,
 1450.00, 1),
(1, 'Colombo 03', 'Galle Face Green', '2025-03-02 08:30:00', 'completed', 5.0, 250.00, 0.00, 15.00, 265.00, 1),
(1, 'Colombo 03', 'Pettah Market', '2025-03-03 10:00:00', 'canceled', 3.0, 150.00, 0.00, 10.00, 160.00, 1),

-- Bookings for customerId 2
(2, 'Nugegoda', 'Colombo Fort', '2025-03-02 09:30:00', 'pending', 10.0, 500.00, 0.00, 25.00, 525.00, 2),
(2, 'Nugegoda', 'Kottawa', '2025-03-04 11:00:00', 'confirmed', 8.0, 400.00, 0.00, 20.00, 420.00, 2),
(2, 'Nugegoda', 'Maharagama', '2025-03-05 14:00:00', 'completed', 6.0, 300.00, 0.00, 15.00, 315.00, 2),

-- Bookings for customerId 3
(3, 'Galle', 'Hikkaduwa', '2025-03-03 14:00:00', 'confirmed', 20.0, 1000.00, 50.00, 30.00, 980.00, 1),
(3, 'Galle', 'Unawatuna', '2025-03-06 16:00:00', 'completed', 15.0, 750.00, 0.00, 25.00, 775.00, 1),
(3, 'Galle', 'Mirissa', '2025-03-07 18:00:00', 'canceled', 25.0, 1250.00, 100.00, 40.00, 1190.00, 1),

-- Bookings for customerId 4
(4, 'Kandy', 'Peradeniya', '2025-03-04 16:30:00', 'canceled', 8.0, 400.00, 0.00, 20.00, 420.00, 3),
(4, 'Kandy', 'Katugastota', '2025-03-08 09:00:00', 'confirmed', 6.0, 300.00, 0.00, 15.00, 315.00, 3),
(4, 'Kandy', 'Nuwara Eliya', '2025-03-09 12:00:00', 'pending', 50.0, 2500.00, 200.00, 100.00, 2400.00, 3),

-- Bookings for customerId 5
(5, 'Negombo', 'Katunayake', '2025-03-05 08:00:00', 'confirmed', 15.0, 750.00, 50.00, 25.00, 725.00, 2),
(5, 'Negombo', 'Colombo City Center', '2025-03-10 10:00:00', 'completed', 30.0, 1500.00, 100.00, 50.00, 1450.00, 2),
(5, 'Negombo', 'Pinnawala Elephant Orphanage', '2025-03-11 13:00:00', 'canceled', 40.0, 2000.00, 150.00, 75.00, 1925.00,
 2),

-- Bookings for customerId 6
(6, 'Matara', 'Weligama', '2025-03-06 11:00:00', 'pending', 12.0, 600.00, 0.00, 30.00, 630.00, 1),
(6, 'Matara', 'Dondra Head', '2025-03-12 15:00:00', 'confirmed', 10.0, 500.00, 0.00, 25.00, 525.00, 1),
(6, 'Matara', 'Tangalle', '2025-03-13 17:00:00', 'completed', 25.0, 1250.00, 100.00, 40.00, 1190.00, 1),

-- Bookings for customerId 7
(7, 'Kurunegala', 'Dambulla', '2025-03-07 13:00:00', 'confirmed', 25.0, 1250.00, 100.00, 40.00, 1190.00, 3),
(7, 'Kurunegala', 'Sigiriya', '2025-03-14 09:00:00', 'completed', 30.0, 1500.00, 100.00, 50.00, 1450.00, 3),
(7, 'Kurunegala', 'Anuradhapura', '2025-03-15 11:00:00', 'canceled', 60.0, 3000.00, 200.00, 120.00, 2920.00, 3),

-- Bookings for customerId 8
(8, 'Colombo 07', 'Bambalapitiya', '2025-03-08 15:00:00', 'confirmed', 5.0, 250.00, 0.00, 15.00, 265.00, 2),
(8, 'Colombo 07', 'Mount Lavinia', '2025-03-16 17:00:00', 'completed', 10.0, 500.00, 0.00, 25.00, 525.00, 2),
(8, 'Colombo 07', 'Dehiwala', '2025-03-17 19:00:00', 'pending', 8.0, 400.00, 0.00, 20.00, 420.00, 2),

-- Bookings for customerId 9
(9, 'Gampaha', 'Ja-Ela', '2025-03-09 10:00:00', 'canceled', 10.0, 500.00, 50.00, 25.00, 475.00, 1),
(9, 'Gampaha', 'Negombo', '2025-03-18 12:00:00', 'confirmed', 15.0, 750.00, 50.00, 25.00, 725.00, 1),
(9, 'Gampaha', 'Kadawatha', '2025-03-19 14:00:00', 'completed', 20.0, 1000.00, 100.00, 30.00, 930.00, 1),

-- Bookings for customerId 10
(10, 'Borella', 'Maradana', '2025-03-10 17:00:00', 'confirmed', 7.0, 350.00, 0.00, 20.00, 370.00, 3),
(10, 'Borella', 'Nugegoda', '2025-03-20 19:00:00', 'completed', 10.0, 500.00, 0.00, 25.00, 525.00, 3),
(10, 'Borella', 'Rajagiriya', '2025-03-21 21:00:00', 'pending', 5.0, 250.00, 0.00, 15.00, 265.00, 3),

-- Additional randomized bookings
(1, 'Colombo 03', 'Mount Lavinia', '2025-03-22 08:00:00', 'confirmed', 12.0, 600.00, 0.00, 30.00, 630.00, 1),
(2, 'Nugegoda', 'Piliyandala', '2025-03-23 09:00:00', 'completed', 7.0, 350.00, 0.00, 20.00, 370.00, 2),
(3, 'Galle', 'Matara', '2025-03-24 10:00:00', 'canceled', 30.0, 1500.00, 100.00, 50.00, 1450.00, 1),
(4, 'Kandy', 'Colombo', '2025-03-25 11:00:00', 'confirmed', 100.0, 5000.00, 300.00, 200.00, 4900.00, 3),
(5, 'Negombo', 'Kandy', '2025-03-26 12:00:00', 'pending', 120.0, 6000.00, 400.00, 250.00, 5850.00, 2),
(6, 'Matara', 'Colombo', '2025-03-27 13:00:00', 'completed', 150.0, 7500.00, 500.00, 300.00, 7300.00, 1),
(7, 'Kurunegala', 'Colombo', '2025-03-28 14:00:00', 'confirmed', 130.0, 6500.00, 400.00, 250.00, 6350.00, 3),
(8, 'Colombo 07', 'Galle', '2025-03-29 15:00:00', 'canceled', 110.0, 5500.00, 300.00, 200.00, 5400.00, 2),
(9, 'Gampaha', 'Colombo', '2025-03-30 16:00:00', 'completed', 20.0, 1000.00, 100.00, 30.00, 930.00, 1),
(10, 'Borella', 'Kandy', '2025-03-31 17:00:00', 'pending', 90.0, 4500.00, 200.00, 150.00, 4450.00, 3);


-- Insert into Booking table (100 bookings spread across 2023, 2024, and 2025)
INSERT INTO Booking (customerId, pickupLocation, destination, pickupTime, status, distance, fare, discount, tax, total,
                     addedUserId)
VALUES
-- Bookings in 2023
(1, 'Colombo 03', 'Bandaranaike Intl Airport', '2023-01-15 07:00:00', 'confirmed', 30.0, 1500.00, 100.00, 50.00,
 1450.00, 1),
(2, 'Nugegoda', 'Colombo Fort', '2023-02-20 09:30:00', 'pending', 10.0, 500.00, 0.00, 25.00, 525.00, 2),
(3, 'Galle', 'Hikkaduwa', '2023-03-25 14:00:00', 'confirmed', 20.0, 1000.00, 50.00, 30.00, 980.00, 1),
(4, 'Kandy', 'Peradeniya', '2023-04-10 16:30:00', 'canceled', 8.0, 400.00, 0.00, 20.00, 420.00, 3),
(5, 'Negombo', 'Katunayake', '2023-05-05 08:00:00', 'confirmed', 15.0, 750.00, 50.00, 25.00, 725.00, 2),
(6, 'Matara', 'Weligama', '2023-06-12 11:00:00', 'pending', 12.0, 600.00, 0.00, 30.00, 630.00, 1),
(7, 'Kurunegala', 'Dambulla', '2023-07-18 13:00:00', 'confirmed', 25.0, 1250.00, 100.00, 40.00, 1190.00, 3),
(8, 'Colombo 07', 'Bambalapitiya', '2023-08-22 15:00:00', 'confirmed', 5.0, 250.00, 0.00, 15.00, 265.00, 2),
(9, 'Gampaha', 'Ja-Ela', '2023-09-30 10:00:00', 'canceled', 10.0, 500.00, 50.00, 25.00, 475.00, 1),
(10, 'Borella', 'Maradana', '2023-10-05 17:00:00', 'confirmed', 7.0, 350.00, 0.00, 20.00, 370.00, 3),

-- Bookings in 2024
(1, 'Colombo 03', 'Mount Lavinia', '2024-01-10 08:00:00', 'confirmed', 12.0, 600.00, 0.00, 30.00, 630.00, 1),
(2, 'Nugegoda', 'Piliyandala', '2024-02-15 09:00:00', 'completed', 7.0, 350.00, 0.00, 20.00, 370.00, 2),
(3, 'Galle', 'Matara', '2024-03-20 10:00:00', 'canceled', 30.0, 1500.00, 100.00, 50.00, 1450.00, 1),
(4, 'Kandy', 'Colombo', '2024-04-25 11:00:00', 'confirmed', 100.0, 5000.00, 300.00, 200.00, 4900.00, 3),
(5, 'Negombo', 'Kandy', '2024-05-30 12:00:00', 'pending', 120.0, 6000.00, 400.00, 250.00, 5850.00, 2),
(6, 'Matara', 'Colombo', '2024-06-05 13:00:00', 'completed', 150.0, 7500.00, 500.00, 300.00, 7300.00, 1),
(7, 'Kurunegala', 'Colombo', '2024-07-10 14:00:00', 'confirmed', 130.0, 6500.00, 400.00, 250.00, 6350.00, 3),
(8, 'Colombo 07', 'Galle', '2024-08-15 15:00:00', 'canceled', 110.0, 5500.00, 300.00, 200.00, 5400.00, 2),
(9, 'Gampaha', 'Colombo', '2024-09-20 16:00:00', 'completed', 20.0, 1000.00, 100.00, 30.00, 930.00, 1),
(10, 'Borella', 'Kandy', '2024-10-25 17:00:00', 'pending', 90.0, 4500.00, 200.00, 150.00, 4450.00, 3),

-- Bookings in 2025
(1, 'Colombo 03', 'Bandaranaike Intl Airport', '2025-01-01 07:00:00', 'confirmed', 30.0, 1500.00, 100.00, 50.00,
 1450.00, 1),
(2, 'Nugegoda', 'Colombo Fort', '2025-02-05 09:30:00', 'pending', 10.0, 500.00, 0.00, 25.00, 525.00, 2),
(3, 'Galle', 'Hikkaduwa', '2025-03-10 14:00:00', 'confirmed', 20.0, 1000.00, 50.00, 30.00, 980.00, 1),
(4, 'Kandy', 'Peradeniya', '2025-04-15 16:30:00', 'canceled', 8.0, 400.00, 0.00, 20.00, 420.00, 3),
(5, 'Negombo', 'Katunayake', '2025-05-20 08:00:00', 'confirmed', 15.0, 750.00, 50.00, 25.00, 725.00, 2),
(6, 'Matara', 'Weligama', '2025-06-25 11:00:00', 'pending', 12.0, 600.00, 0.00, 30.00, 630.00, 1),
(7, 'Kurunegala', 'Dambulla', '2025-07-30 13:00:00', 'confirmed', 25.0, 1250.00, 100.00, 40.00, 1190.00, 3),
(8, 'Colombo 07', 'Bambalapitiya', '2025-08-05 15:00:00', 'confirmed', 5.0, 250.00, 0.00, 15.00, 265.00, 2),
(9, 'Gampaha', 'Ja-Ela', '2025-09-10 10:00:00', 'canceled', 10.0, 500.00, 50.00, 25.00, 475.00, 1),
(10, 'Borella', 'Maradana', '2025-10-15 17:00:00', 'confirmed', 7.0, 350.00, 0.00, 20.00, 370.00, 3),

-- Additional randomized bookings across 2023, 2024, and 2025
(1, 'Colombo 03', 'Mount Lavinia', '2023-11-20 08:00:00', 'confirmed', 12.0, 600.00, 0.00, 30.00, 630.00, 1),
(2, 'Nugegoda', 'Piliyandala', '2024-12-25 09:00:00', 'completed', 7.0, 350.00, 0.00, 20.00, 370.00, 2),
(3, 'Galle', 'Matara', '2025-01-30 10:00:00', 'canceled', 30.0, 1500.00, 100.00, 50.00, 1450.00, 1),
(4, 'Kandy', 'Colombo', '2023-02-15 11:00:00', 'confirmed', 100.0, 5000.00, 300.00, 200.00, 4900.00, 3),
(5, 'Negombo', 'Kandy', '2024-03-20 12:00:00', 'pending', 120.0, 6000.00, 400.00, 250.00, 5850.00, 2),
(6, 'Matara', 'Colombo', '2025-04-25 13:00:00', 'completed', 150.0, 7500.00, 500.00, 300.00, 7300.00, 1),
(7, 'Kurunegala', 'Colombo', '2023-05-30 14:00:00', 'confirmed', 130.0, 6500.00, 400.00, 250.00, 6350.00, 3),
(8, 'Colombo 07', 'Galle', '2024-06-05 15:00:00', 'canceled', 110.0, 5500.00, 300.00, 200.00, 5400.00, 2),
(9, 'Gampaha', 'Colombo', '2025-07-10 16:00:00', 'completed', 20.0, 1000.00, 100.00, 30.00, 930.00, 1),
(10, 'Borella', 'Kandy', '2023-08-15 17:00:00', 'pending', 90.0, 4500.00, 200.00, 150.00, 4450.00, 3);