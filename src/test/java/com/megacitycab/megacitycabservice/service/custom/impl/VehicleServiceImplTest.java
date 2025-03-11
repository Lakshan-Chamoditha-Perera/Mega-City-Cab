package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.custom.VehicleService;
import com.megacitycab.megacitycabservice.util.TransactionManager;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleServiceImplTest {

    private static TransactionManager transactionManager;
    private static VehicleService vehicleService;
    private static Integer testVehicleId;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        transactionManager = new TransactionManager();
        vehicleService = new VehicleServiceImpl(transactionManager);
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving a New Vehicle")
    void testSaveVehicle() throws MegaCityCabException {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model X") // Valid model
                .brand("Tesla") // Valid brand
                .passengerCount(4) // Valid passenger count
                .color("Red") // Valid color
                .availability(true)
                .driverId(27) // Valid driver ID (from the provided list)
                .addedUserId(1)
                .pricePerKm(10.5f) // Valid price per km
                .build();

        Boolean result = vehicleService.saveVehicle(vehicleDTO);
        assertTrue(result, "Vehicle should be saved successfully");

        // Fetch the vehicle to get ID for further tests
        List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
        testVehicleId = vehicles.stream()
                .filter(vehicle -> vehicle.getLicensePlate().equals("ABC-1234"))
                .findFirst()
                .map(VehicleDTO::getVehicleId)
                .orElse(null);

        assertNotNull(testVehicleId, "Saved vehicle should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Saving Vehicle with Invalid License Plate")
    void testSaveVehicleWithInvalidLicensePlate() {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC1234") // Invalid license plate (missing hyphen)
                .model("Model X")
                .brand("Tesla")
                .passengerCount(4)
                .color("Red")
                .availability(true)
                .driverId(28) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(10.5f)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> vehicleService.saveVehicle(vehicleDTO)
        );

        assertEquals(ErrorMessage.INVALID_LICENSE_PLATE.getMessage(), exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test Saving Vehicle with Invalid Model")
    void testSaveVehicleWithInvalidModel() {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model@X") // Invalid model (contains special character)
                .brand("Tesla")
                .passengerCount(4)
                .color("Red")
                .availability(true)
                .driverId(29) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(10.5f)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> vehicleService.saveVehicle(vehicleDTO)
        );

        assertEquals(ErrorMessage.INVALID_MODEL.getMessage(), exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test Saving Vehicle with Invalid Brand")
    void testSaveVehicleWithInvalidBrand() {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model X")
                .brand("Tesla123") // Invalid brand (contains numbers)
                .passengerCount(4)
                .color("Red")
                .availability(true)
                .driverId(30) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(10.5f)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> vehicleService.saveVehicle(vehicleDTO)
        );

        assertEquals(ErrorMessage.INVALID_BRAND.getMessage(), exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Test Saving Vehicle with Invalid Color")
    void testSaveVehicleWithInvalidColor() {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model X")
                .brand("Tesla")
                .passengerCount(4)
                .color("Red123") // Invalid color (contains numbers)
                .availability(true)
                .driverId(31) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(10.5f)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> vehicleService.saveVehicle(vehicleDTO)
        );

        assertEquals(ErrorMessage.INVALID_COLOR.getMessage(), exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Test Saving Vehicle with Invalid Passenger Count")
    void testSaveVehicleWithInvalidPassengerCount() {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model X")
                .brand("Tesla")
                .passengerCount(0) // Invalid passenger count (must be positive)
                .color("Red")
                .availability(true)
                .driverId(27) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(10.5f)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> vehicleService.saveVehicle(vehicleDTO)
        );

        assertEquals(ErrorMessage.INVALID_PASSENGER_COUNT.getMessage(), exception.getMessage());
    }

    @Test
    @Order(7)
    @DisplayName("Test Saving Vehicle with Invalid Price Per Km")
    void testSaveVehicleWithInvalidPricePerKm() {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model X")
                .brand("Tesla")
                .passengerCount(4)
                .color("Red")
                .availability(true)
                .driverId(28) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(-10.5f) // Invalid price per km (must be positive)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> vehicleService.saveVehicle(vehicleDTO)
        );

        assertEquals(ErrorMessage.INVALID_PRICE_PER_KM.getMessage(), exception.getMessage());
    }

    @Test
    @Order(8)
    @DisplayName("Test Fetching All Vehicles with Driver")
    void testGetAllVehiclesWithDriver() throws MegaCityCabException {
        List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
        assertFalse(vehicles.isEmpty(), "There should be at least one vehicle");
    }

    @Test
    @Order(9)
    @DisplayName("Test Updating Vehicle")
    void testUpdateVehicle() throws MegaCityCabException {
        assertNotNull(testVehicleId, "Test vehicle ID should not be null");

        VehicleDTO updatedVehicle = VehicleDTO.builder()
                .vehicleId(testVehicleId)
                .licensePlate("XYZ-5678") // Updated license plate
                .model("Model Y") // Updated model
                .brand("Tesla")
                .passengerCount(5) // Updated passenger count
                .color("Blue") // Updated color
                .availability(false) // Updated availability
                .driverId(29) // Updated driver ID
                .addedUserId(1)
                .pricePerKm(15.0f) // Updated price per km
                .build();

        Boolean result = vehicleService.updateVehicle(updatedVehicle);
        assertTrue(result, "Vehicle should be updated successfully");

        // Fetch and verify update
        List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
        boolean isUpdated = vehicles.stream()
                .anyMatch(vehicle -> vehicle.getVehicleId() == testVehicleId
                        && vehicle.getLicensePlate().equals("XYZ-5678"));

        assertTrue(isUpdated, "Updated vehicle should exist in DB");
    }

    @Test
    @Order(10)
    @DisplayName("Test Deleting a Vehicle")
    void testDeleteVehicle() throws MegaCityCabException {
        assertNotNull(testVehicleId, "Test vehicle ID should not be null");

        Boolean result = vehicleService.deleteVehicle(testVehicleId);
        assertTrue(result, "Vehicle should be deleted successfully");

        // Verify deletion
        List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
        boolean exists = vehicles.stream().anyMatch(vehicle -> vehicle.getVehicleId() == testVehicleId);

        assertFalse(exists, "Deleted vehicle should not exist in DB");
    }

    @Test
    @Order(11)
    @DisplayName("Test Get Vehicles Count")
    void testGetVehiclesCount() throws MegaCityCabException {
        Integer count = vehicleService.getVehiclesCount();
        assertNotNull(count, "Vehicle count should not be null");
        assertTrue(count >= 0, "Vehicle count should be non-negative");
    }

    @Test
    @Order(12)
    @DisplayName("Test Saving Multiple Vehicles with Different Driver IDs")
    void testSaveMultipleVehicles() throws MegaCityCabException {
        // Vehicle 1
        VehicleDTO vehicle1 = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model X")
                .brand("Tesla")
                .passengerCount(4)
                .color("Red")
                .availability(true)
                .driverId(27) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(10.5f)
                .build();

        // Vehicle 2
        VehicleDTO vehicle2 = VehicleDTO.builder()
                .licensePlate("DEF-5678")
                .model("Model Y")
                .brand("Toyota")
                .passengerCount(5)
                .color("Blue")
                .availability(true)
                .driverId(28) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(12.0f)
                .build();

        // Vehicle 3
        VehicleDTO vehicle3 = VehicleDTO.builder()
                .licensePlate("GHI-9012")
                .model("Model Z")
                .brand("Honda")
                .passengerCount(6)
                .color("Green")
                .availability(true)
                .driverId(29) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(14.5f)
                .build();

        // Vehicle 4
        VehicleDTO vehicle4 = VehicleDTO.builder()
                .licensePlate("JKL-3456")
                .model("Model A")
                .brand("Nissan")
                .passengerCount(7)
                .color("Yellow")
                .availability(true)
                .driverId(30) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(16.0f)
                .build();

        // Vehicle 5
        VehicleDTO vehicle5 = VehicleDTO.builder()
                .licensePlate("MNO-7890")
                .model("Model B")
                .brand("Ford")
                .passengerCount(8)
                .color("Black")
                .availability(true)
                .driverId(31) // Valid driver ID
                .addedUserId(1)
                .pricePerKm(18.5f)
                .build();

        // Save all vehicles
        assertTrue(vehicleService.saveVehicle(vehicle1), "Vehicle 1 should be saved successfully");
        assertTrue(vehicleService.saveVehicle(vehicle2), "Vehicle 2 should be saved successfully");
        assertTrue(vehicleService.saveVehicle(vehicle3), "Vehicle 3 should be saved successfully");
        assertTrue(vehicleService.saveVehicle(vehicle4), "Vehicle 4 should be saved successfully");
        assertTrue(vehicleService.saveVehicle(vehicle5), "Vehicle 5 should be saved successfully");

        // Verify all vehicles exist
        List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
        assertEquals(5, vehicles.size(), "All 5 vehicles should exist in DB");
    }
}