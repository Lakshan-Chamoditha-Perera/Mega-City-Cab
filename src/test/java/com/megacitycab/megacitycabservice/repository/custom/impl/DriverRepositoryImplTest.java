package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.configuration.db.SingleDatabaseConnection;
import com.megacitycab.megacitycabservice.entity.custom.Driver;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverRepositoryImplTest {

    static Connection connection;
    private static DriverRepositoryImpl driverRepository;
    private static Integer testDriverId1=53;
    private static Integer testDriverId2;
    private static Integer testDriverId3;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        connection = SingleDatabaseConnection.getInstance().getConnection();
        driverRepository = new DriverRepositoryImpl();
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving Multiple Drivers")
    void testSaveDrivers() throws SQLException {
        // Save Driver 1
        Driver driver1 = Driver.builder()
                .firstName("Chaminda")
                .lastName("Perera")
                .licenseNumber("LIC123456")
                .mobileNo("0771234567")
                .email("chaminda.perera@example.com")
                .addedUserId(1)
                .build();
        assertTrue(driverRepository.save(driver1, connection), "Driver 1 should be saved successfully");

        // Save Driver 2
        Driver driver2 = Driver.builder()
                .firstName("Nirosha")
                .lastName("Fernando")
                .licenseNumber("LIC654321")
                .mobileNo("0777654321")
                .email("nirosha.fernando@example.com")
                .addedUserId(1)
                .build();
        assertTrue(driverRepository.save(driver2, connection), "Driver 2 should be saved successfully");

        // Save Driver 3
        Driver driver3 = Driver.builder()
                .firstName("Kamal")
                .lastName("Gunawardena")
                .licenseNumber("LIC987654")
                .mobileNo("0779876543")
                .email("kamal.gunawardena@example.com")
                .addedUserId(1)
                .build();
        assertTrue(driverRepository.save(driver3, connection), "Driver 3 should be saved successfully");

        // Fetch IDs for further tests
        List<Driver> drivers = driverRepository.findAll(connection);
        testDriverId1 = drivers.stream()
                .filter(d -> d.getEmail().equals("chaminda.perera@example.com"))
                .findFirst()
                .map(Driver::getDriverId)
                .orElse(null);
        testDriverId2 = drivers.stream()
                .filter(d -> d.getEmail().equals("nirosha.fernando@example.com"))
                .findFirst()
                .map(Driver::getDriverId)
                .orElse(null);
        testDriverId3 = drivers.stream()
                .filter(d -> d.getEmail().equals("kamal.gunawardena@example.com"))
                .findFirst()
                .map(Driver::getDriverId)
                .orElse(null);

        assertNotNull(testDriverId1, "Driver 1 should exist in DB");
        assertNotNull(testDriverId2, "Driver 2 should exist in DB");
        assertNotNull(testDriverId3, "Driver 3 should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Saving Driver with Duplicate Email")
    void testSaveDriverWithDuplicateEmail() throws SQLException {
        // Attempt to save a driver with a duplicate email
        Driver duplicateEmailDriver = Driver.builder()
                .firstName("Sangeetha")
                .lastName("Rajapakse")
                .licenseNumber("LIC111111")
                .mobileNo("0771111111")
                .email("chaminda.perera@example.com") // Duplicate email
                .addedUserId(1)
                .build();

        SQLException exception = assertThrows(
                SQLException.class,
                () -> driverRepository.save(duplicateEmailDriver, connection)
        );
        assertTrue(exception.getMessage().contains("Duplicate entry"), "Should throw SQLException for duplicate email");
    }

    @Test
    @Order(3)
    @DisplayName("Test Fetching All Drivers")
    void testFindAllDrivers() throws SQLException {
        List<Driver> drivers = driverRepository.findAll(connection);
        assertFalse(drivers.isEmpty(), "There should be at least one driver");
        assertEquals(3, drivers.size(), "There should be exactly 3 drivers in the database");
    }

    @Test
    @Order(4)
    @DisplayName("Test Finding Drivers by ID")
    void testFindDriversById() throws SQLException {
        // Find Driver 1
        Driver driver1 = driverRepository.findById(testDriverId1, connection);
        assertNotNull(driver1, "Driver 1 should exist in DB");
        assertEquals("Chaminda", driver1.getFirstName(), "First name should match for Driver 1");
        assertEquals("Perera", driver1.getLastName(), "Last name should match for Driver 1");

        // Find Driver 2
        Driver driver2 = driverRepository.findById(testDriverId2, connection);
        assertNotNull(driver2, "Driver 2 should exist in DB");
        assertEquals("Nirosha", driver2.getFirstName(), "First name should match for Driver 2");
        assertEquals("Fernando", driver2.getLastName(), "Last name should match for Driver 2");

        // Find Driver 3
        Driver driver3 = driverRepository.findById(testDriverId3, connection);
        assertNotNull(driver3, "Driver 3 should exist in DB");
        assertEquals("Kamal", driver3.getFirstName(), "First name should match for Driver 3");
        assertEquals("Gunawardena", driver3.getLastName(), "Last name should match for Driver 3");
    }

    @Test
    @Order(5)
    @DisplayName("Test Updating Drivers")
    void testUpdateDrivers() throws SQLException {
        // Update Driver 1
        Driver updatedDriver1 = Driver.builder()
                .driverId(testDriverId1)
                .firstName("Tharindu")
                .lastName("Silva")
                .licenseNumber("LIC222222")
                .mobileNo("0772222222")
                .email("tharindu.silva@example.com")
                .build();
        assertTrue(driverRepository.updateById(updatedDriver1, connection), "Driver 1 should be updated successfully");

        // Update Driver 2
        Driver updatedDriver2 = Driver.builder()
                .driverId(testDriverId2)
                .firstName("Dilani")
                .lastName("Weerasinghe")
                .licenseNumber("LIC333333")
                .mobileNo("0773333333")
                .email("dilani.weerasinghe@example.com")
                .build();
        assertTrue(driverRepository.updateById(updatedDriver2, connection), "Driver 2 should be updated successfully");

        // Update Driver 3
        Driver updatedDriver3 = Driver.builder()
                .driverId(testDriverId3)
                .firstName("Ramesh")
                .lastName("Jayasinghe")
                .licenseNumber("LIC444444")
                .mobileNo("0774444444")
                .email("ramesh.jayasinghe@example.com")
                .build();
        assertTrue(driverRepository.updateById(updatedDriver3, connection), "Driver 3 should be updated successfully");

        // Verify updates
        Driver driver1 = driverRepository.findById(testDriverId1, connection);
        assertEquals("Tharindu", driver1.getFirstName(), "First name should be updated for Driver 1");
        assertEquals("Silva", driver1.getLastName(), "Last name should be updated for Driver 1");

        Driver driver2 = driverRepository.findById(testDriverId2, connection);
        assertEquals("Dilani", driver2.getFirstName(), "First name should be updated for Driver 2");
        assertEquals("Weerasinghe", driver2.getLastName(), "Last name should be updated for Driver 2");

        Driver driver3 = driverRepository.findById(testDriverId3, connection);
        assertEquals("Ramesh", driver3.getFirstName(), "First name should be updated for Driver 3");
        assertEquals("Jayasinghe", driver3.getLastName(), "Last name should be updated for Driver 3");
    }

    @Test
    @Order(6)
    @DisplayName("Test Deleting Drivers")
    void testDeleteDrivers() throws SQLException {
        // Delete Driver 1
        assertTrue(driverRepository.delete(testDriverId1, connection), "Driver 1 should be deleted successfully");
        assertNull(driverRepository.findById(testDriverId1, connection), "Driver 1 should not exist in DB after deletion");

        // Delete Driver 2
        assertTrue(driverRepository.delete(testDriverId2, connection), "Driver 2 should be deleted successfully");
        assertNull(driverRepository.findById(testDriverId2, connection), "Driver 2 should not exist in DB after deletion");

        // Delete Driver 3
        assertTrue(driverRepository.delete(testDriverId3, connection), "Driver 3 should be deleted successfully");
        assertNull(driverRepository.findById(testDriverId3, connection), "Driver 3 should not exist in DB after deletion");
    }

    @Test
    @Order(7)
    @DisplayName("Test Getting Driver Count")
    void testGetDriverCount() throws SQLException {
        Integer count = driverRepository.getCount(connection);
        assertNotNull(count, "Driver count should not be null");
        assertTrue(count >= 0, "Driver count should be non-negative");
    }

    @Test
    @Order(8)
    @DisplayName("Test Checking if Drivers Exist by ID")
    void testExistsById() throws SQLException {
        assertTrue(driverRepository.existsById(testDriverId1, connection), "Driver 1 should exist in DB");
        assertTrue(driverRepository.existsById(testDriverId2, connection), "Driver 2 should exist in DB");
        assertTrue(driverRepository.existsById(testDriverId3, connection), "Driver 3 should exist in DB");
    }

    @Test
    @Order(9)
    @DisplayName("Test Checking if Drivers Exist by Email")
    void testExistsByEmail() throws SQLException {
        assertTrue(driverRepository.existsByEmail("chaminda.perera@example.com", connection), "Driver 1 email should exist in DB");
        assertTrue(driverRepository.existsByEmail("nirosha.fernando@example.com", connection), "Driver 2 email should exist in DB");
        assertTrue(driverRepository.existsByEmail("kamal.gunawardena@example.com", connection), "Driver 3 email should exist in DB");
    }

    @Test
    @Order(10)
    @DisplayName("Test Checking if Drivers Exist by Mobile Number")
    void testExistsByMobileNumber() throws SQLException {
        assertTrue(driverRepository.existsByMobile("0771234567", connection), "Driver 1 mobile number should exist in DB");
        assertTrue(driverRepository.existsByMobile("0777654321", connection), "Driver 2 mobile number should exist in DB");
        assertTrue(driverRepository.existsByMobile("0779876543", connection), "Driver 3 mobile number should exist in DB");
    }
}