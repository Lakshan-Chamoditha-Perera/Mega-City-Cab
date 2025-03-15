package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.DriverDTO;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
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
    @DisplayName("Test Saving a New Driver (Valid)")
    void testSaveDriver() throws MegaCityCabException {
        DriverDTO driverDTO = DriverDTO.builder()
                .firstName("Kamal")
                .lastName("Perera")
                .licenseNumber("B1234567")
                .mobileNo("0771234567")
                .email("kamal.perera@example.com")
                .addedUserId(1)
                .build();

        Boolean result = driverService.saveDriver(driverDTO);
        assertTrue(result, "Driver should be saved successfully");

        // Fetch the driver to get ID for further tests
        List<DriverDTO> drivers = driverService.getAllDrivers();
        testDriverId = drivers.stream()
                .filter(driver -> driver.getEmail().equals("kamal.perera@example.com"))
                .findFirst()
                .map(DriverDTO::getDriverId)
                .orElse(null);

        assertNotNull(testDriverId, "Saved driver should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Saving Driver with Invalid First Name")
    void testSaveDriverWithInvalidFirstName() {
        DriverDTO driverDTO = DriverDTO.builder()
                .firstName("Kamal123") // Invalid first name (contains numbers)
                .lastName("Perera")
                .licenseNumber("B1234567")
                .mobileNo("0771234567")
                .email("kamal.perera@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> driverService.saveDriver(driverDTO)
        );

        assertEquals(ErrorMessage.INVALID_FIRST_NAME.getMessage(), exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test Saving Driver with Invalid Last Name")
    void testSaveDriverWithInvalidLastName() {
        DriverDTO driverDTO = DriverDTO.builder()
                .firstName("Kamal")
                .lastName("Perera123") // Invalid last name (contains numbers)
                .licenseNumber("B1234567")
                .mobileNo("0771234567")
                .email("kamal.perera@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> driverService.saveDriver(driverDTO)
        );

        assertEquals(ErrorMessage.INVALID_LAST_NAME.getMessage(), exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test Saving Driver with Invalid License Number")
    void testSaveDriverWithInvalidLicenseNumber() {
        DriverDTO driverDTO = DriverDTO.builder()
                .firstName("Kamal")
                .lastName("Perera")
                .licenseNumber("B123") // Invalid license number (less than 8 characters)
                .mobileNo("0771234567")
                .email("kamal.perera@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> driverService.saveDriver(driverDTO)
        );

        assertEquals(ErrorMessage.INVALID_DRIVER_LICENSE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Test Saving Driver with Invalid Mobile Number")
    void testSaveDriverWithInvalidMobileNumber() {
        DriverDTO driverDTO = DriverDTO.builder()
                .firstName("Kamal")
                .lastName("Perera")
                .licenseNumber("B1234567")
                .mobileNo("077123456") // Invalid mobile number (less than 10 digits)
                .email("kamal.perera@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> driverService.saveDriver(driverDTO)
        );

        assertEquals(ErrorMessage.INVALID_MOBILE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Test Saving Driver with Invalid Email")
    void testSaveDriverWithInvalidEmail() {
        DriverDTO driverDTO = DriverDTO.builder()
                .firstName("Kamal")
                .lastName("Perera")
                .licenseNumber("B1234567")
                .mobileNo("0771234567")
                .email("kamal.perera@example") // Invalid email (missing domain)
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> driverService.saveDriver(driverDTO)
        );

        assertEquals(ErrorMessage.DRIVER_MOBILE_ALREADY_EXISTS.getMessage(), exception.getMessage());
    }

    @Test
    @Order(7)
    @DisplayName("Test Fetching All Drivers")
    void testGetAllDrivers() throws MegaCityCabException {
        List<DriverDTO> drivers = driverService.getAllDrivers();
        assertFalse(drivers.isEmpty(), "There should be at least one driver");
    }

    @Test
    @Order(8)
    @DisplayName("Test Updating Driver")
    void testUpdateDriver() throws MegaCityCabException {
        assertNotNull(1, "Test driver ID should not be null");

        DriverDTO updatedDriver = DriverDTO.builder()
                .driverId(1)
                .firstName("Nimal")
                .lastName("Fernando")
                .licenseNumber("C9876543")
                .mobileNo("0777654321")
                .email("nimal.fernando@example.com")
                .build();

        Boolean result = driverService.updateDriver(updatedDriver);
        assertTrue(result, "Driver should be updated successfully");

        List<DriverDTO> drivers = driverService.getAllDrivers();
        boolean isUpdated = drivers.stream()
                .anyMatch(driver -> driver.getDriverId() == 1
                        && driver.getFirstName().equals("Nimal")
                        && driver.getLastName().equals("Fernando"));

        assertTrue(isUpdated, "Updated driver should exist in DB");
    }

    @Test
    @Order(9)
    @DisplayName("Test Deleting a Driver")
    void testDeleteDriver() throws MegaCityCabException {
        assertNotNull(1, "Test driver ID should not be null");

        Boolean result = driverService.deleteDriver(1);
        assertTrue(result, "Driver should be deleted successfully");

        List<DriverDTO> drivers = driverService.getAllDrivers();
        boolean exists = drivers.stream().anyMatch(driver -> driver.getDriverId() == 1);

        assertFalse(exists, "Deleted driver should not exist in DB");
    }

    @Test
    @Order(10)
    @DisplayName("Test Get Drivers Count")
    void testGetDriversCount() throws MegaCityCabException {
        Integer count = driverService.getDriversCount();
        assertNotNull(count, "Driver count should not be null");
        assertTrue(count >= 0, "Driver count should be non-negative");
    }

    @Test
    @Order(11)
    @DisplayName("Test Saving Multiple Drivers")
    void testSaveMultipleDrivers() throws MegaCityCabException {
        // Driver 1
        DriverDTO driver1 = DriverDTO.builder()
                .firstName("Sunil")
                .lastName("Jayasinghe")
                .licenseNumber("D1234567")
                .mobileNo("0771111111")
                .email("sunil.jayasinghe@example.com")
                .addedUserId(1)
                .build();

        // Driver 2
        DriverDTO driver2 = DriverDTO.builder()
                .firstName("Anil")
                .lastName("Gunathilake")
                .licenseNumber("E2345678")
                .mobileNo("0772222222")
                .email("anil.gunathilake@example.com")
                .addedUserId(1)
                .build();

        // Driver 3
        DriverDTO driver3 = DriverDTO.builder()
                .firstName("Saman")
                .lastName("Silva")
                .licenseNumber("F3456789")
                .mobileNo("0773333333")
                .email("saman.silva@example.com")
                .addedUserId(1)
                .build();

        // Driver 4
        DriverDTO driver4 = DriverDTO.builder()
                .firstName("Ruwan")
                .lastName("Bandara")
                .licenseNumber("G4567890")
                .mobileNo("0774444444")
                .email("ruwan.bandara@example.com")
                .addedUserId(1)
                .build();

        // Driver 5
        DriverDTO driver5 = DriverDTO.builder()
                .firstName("Chamara")
                .lastName("Wijesinghe")
                .licenseNumber("H5678901")
                .mobileNo("0775555555")
                .email("chamara.wijesinghe@example.com")
                .addedUserId(1)
                .build();

        assertTrue(driverService.saveDriver(driver1), "Driver 1 should be saved successfully");
        assertTrue(driverService.saveDriver(driver2), "Driver 2 should be saved successfully");
        assertTrue(driverService.saveDriver(driver3), "Driver 3 should be saved successfully");
        assertTrue(driverService.saveDriver(driver4), "Driver 4 should be saved successfully");
        assertTrue(driverService.saveDriver(driver5), "Driver 5 should be saved successfully");

        List<DriverDTO> drivers = driverService.getAllDrivers();
        assertEquals(5, drivers.size(), "All 5 drivers should exist in DB");
    }
}