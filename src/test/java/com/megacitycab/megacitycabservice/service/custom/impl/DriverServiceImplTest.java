package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.DriverDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.custom.DriverService;
import com.megacitycab.megacitycabservice.util.TransactionManager;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverServiceImplTest {

    private static TransactionManager transactionManager;
    private static DriverService driverService;
    private static Integer testDriverId;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        transactionManager = new TransactionManager();
        driverService = new DriverServiceImpl(transactionManager);
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving a New Driver")
    void testSaveDriver() throws MegaCityCabException {
        DriverDTO driverDTO = DriverDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .licenseNumber("ABC123456")
                .mobileNo("0771234567")
                .email("john.doe@example.com")
                .build();

        Boolean result = driverService.saveDriver(driverDTO);
        assertTrue(result, "Driver should be saved successfully");

        // Fetch the driver to get ID for further tests
        List<DriverDTO> drivers = driverService.getAllDrivers();
        testDriverId = drivers.stream()
                .filter(driver -> driver.getEmail().equals("john.doe@example.com"))
                .findFirst()
                .map(DriverDTO::getDriverId)
                .orElse(null);

        assertNotNull(testDriverId, "Saved driver should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Fetching All Drivers")
    void testGetAllDrivers() throws MegaCityCabException {
        List<DriverDTO> drivers = driverService.getAllDrivers();
        assertFalse(drivers.isEmpty(), "There should be at least one driver");
    }

    @Test
    @Order(3)
    @DisplayName("Test Updating Driver")
    void testUpdateDriver() throws MegaCityCabException {
        assertNotNull(testDriverId, "Test driver ID should not be null");

        DriverDTO updatedDriver = DriverDTO.builder()
                .driverId(testDriverId)
                .firstName("Jane")
                .lastName("Doe")
                .licenseNumber("XYZ987654")
                .mobileNo("0777654321")
                .email("jane.doe@example.com")
                .build();

        Boolean result = driverService.updateDriver(updatedDriver);
        assertTrue(result, "Driver should be updated successfully");

        // Fetch and verify update
        List<DriverDTO> drivers = driverService.getAllDrivers();
        boolean isUpdated = drivers.stream()
                .anyMatch(driver -> driver.getDriverId() == testDriverId
                        && driver.getEmail().equals("jane.doe@example.com"));

        assertTrue(isUpdated, "Updated driver should exist in DB");
    }

    @Test
    @Order(4)
    @DisplayName("Test Deleting a Driver")
    void testDeleteDriver() throws MegaCityCabException {
        assertNotNull(testDriverId, "Test driver ID should not be null");

        Boolean result = driverService.deleteDriver(testDriverId);
        assertTrue(result, "Driver should be deleted successfully");

        // Verify deletion
        List<DriverDTO> drivers = driverService.getAllDrivers();
        boolean exists = drivers.stream().anyMatch(driver -> driver.getDriverId() == testDriverId);

        assertFalse(exists, "Deleted driver should not exist in DB");
    }

    @Test
    @Order(5)
    @DisplayName("Test Get Drivers Count")
    void testGetDriversCount() throws MegaCityCabException {
        Integer count = driverService.getDriversCount();
        assertNotNull(count, "Driver count should not be null");
        assertTrue(count >= 0, "Driver count should be non-negative");
    }

    @AfterAll
    static void cleanUp() {
        // Cleanup if necessary
        System.out.println("Tests completed.");
    }
}