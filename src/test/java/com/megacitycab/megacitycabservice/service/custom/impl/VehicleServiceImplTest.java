package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
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
        // Initialize transaction manager and service
        transactionManager = new TransactionManager(); // Ensure this connects to your test DB
        vehicleService = new VehicleServiceImpl(transactionManager);
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving a New Vehicle")
    void testSaveVehicle() throws MegaCityCabException {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .licensePlate("ABC-1234")
                .model("Model X")
                .brand("Tesla")
                .passengerCount(4)
                .color("Red")
                .availability(true)
                .driverId(1) // Assuming driver with ID 1 exists
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
    @DisplayName("Test Fetching All Vehicles with Driver")
    void testGetAllVehiclesWithDriver() throws MegaCityCabException {
        List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
        assertFalse(vehicles.isEmpty(), "There should be at least one vehicle");
    }

    @Test
    @Order(3)
    @DisplayName("Test Updating Vehicle")
    void testUpdateVehicle() throws MegaCityCabException {
        assertNotNull(testVehicleId, "Test vehicle ID should not be null");

        VehicleDTO updatedVehicle = VehicleDTO.builder()
                .vehicleId(testVehicleId)
                .licensePlate("XYZ-5678")
                .model("Model Y")
                .brand("Tesla")
                .passengerCount(5)
                .color("Blue")
                .availability(false)
                .driverId(2) // Assuming driver with ID 2 exists
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
    @Order(4)
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
    @Order(5)
    @DisplayName("Test Get Vehicles Count")
    void testGetVehiclesCount() throws MegaCityCabException {
        Integer count = vehicleService.getVehiclesCount();
        assertNotNull(count, "Vehicle count should not be null");
        assertTrue(count >= 0, "Vehicle count should be non-negative");
    }
}