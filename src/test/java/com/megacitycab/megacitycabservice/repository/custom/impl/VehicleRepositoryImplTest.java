package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.configuration.db.SingleDatabaseConnection;
import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.entity.custom.Vehicle;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleRepositoryImplTest {

    static Connection connection;
    private static VehicleRepositoryImpl vehicleRepository;
    private static Integer testVehicleId1;
    private static Integer testVehicleId2;
    private static Integer testVehicleId3;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        connection = SingleDatabaseConnection.getInstance().getConnection();
        vehicleRepository = new VehicleRepositoryImpl();
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving Multiple Vehicles")
    void testSaveVehicles() throws SQLException {
        // Save Vehicle 1
        Vehicle vehicle1 = Vehicle.builder()
                .licensePlate("CAR-1234")
                .model("Toyota Corolla")
                .brand("Toyota")
                .passengerCount(4)
                .color("White")
                .availability(true)
                .driverId(35)
                .addedUserId(1)
                .pricePerKm(50.0f)
                .build();
        assertTrue(vehicleRepository.save(vehicle1, connection), "Vehicle 1 should be saved successfully");

        // Save Vehicle 2
        Vehicle vehicle2 = Vehicle.builder()
                .licensePlate("CAR-5678")
                .model("Suzuki Alto")
                .brand("Suzuki")
                .passengerCount(4)
                .color("Red")
                .availability(true)
                .driverId(2)
                .addedUserId(1)
                .pricePerKm(40.0f)
                .build();
        assertTrue(vehicleRepository.save(vehicle2, connection), "Vehicle 2 should be saved successfully");

        // Save Vehicle 3
        Vehicle vehicle3 = Vehicle.builder()
                .licensePlate("CAR-9101")
                .model("Nissan Sunny")
                .brand("Nissan")
                .passengerCount(4)
                .color("Blue")
                .availability(true)
                .driverId(3)
                .addedUserId(1)
                .pricePerKm(60.0f)
                .build();
        assertTrue(vehicleRepository.save(vehicle3, connection), "Vehicle 3 should be saved successfully");

        // Fetch IDs for further tests
        testVehicleId1 = vehicleRepository.findAll(connection).stream()
                .filter(v -> v.getLicensePlate().equals("CAR-1234"))
                .findFirst()
                .map(Vehicle::getVehicleId)
                .orElse(null);
        testVehicleId2 = vehicleRepository.findAll(connection).stream()
                .filter(v -> v.getLicensePlate().equals("CAR-5678"))
                .findFirst()
                .map(Vehicle::getVehicleId)
                .orElse(null);
        testVehicleId3 = vehicleRepository.findAll(connection).stream()
                .filter(v -> v.getLicensePlate().equals("CAR-9101"))
                .findFirst()
                .map(Vehicle::getVehicleId)
                .orElse(null);

        assertNotNull(testVehicleId1, "Vehicle 1 should exist in DB");
        assertNotNull(testVehicleId2, "Vehicle 2 should exist in DB");
        assertNotNull(testVehicleId3, "Vehicle 3 should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Saving Vehicle with Duplicate License Plate")
    void testSaveVehicleWithDuplicateLicensePlate() throws SQLException {
        Vehicle duplicateLicensePlateVehicle = Vehicle.builder()
                .licensePlate("CAR-1234") // Duplicate license plate
                .model("Toyota Corolla")
                .brand("Toyota")
                .passengerCount(4)
                .color("White")
                .availability(true)
                .driverId(1)
                .addedUserId(1)
                .pricePerKm(50.0f)
                .build();

        SQLException exception = assertThrows(
                SQLException.class,
                () -> vehicleRepository.save(duplicateLicensePlateVehicle, connection)
        );
        assertTrue(exception.getMessage().contains("Duplicate entry"), "Should throw SQLException for duplicate license plate");
    }

    @Test
    @Order(3)
    @DisplayName("Test Fetching All Vehicles")
    void testFindAllVehicles() throws SQLException {
        List<Vehicle> vehicles = vehicleRepository.findAll(connection);
        assertFalse(vehicles.isEmpty(), "There should be at least one vehicle");
        assertEquals(3, vehicles.size(), "There should be exactly 3 vehicles in the database");
    }

    @Test
    @Order(4)
    @DisplayName("Test Finding Vehicles by ID")
    void testFindVehiclesById() throws SQLException {
        // Find Vehicle 1
        Vehicle vehicle1 = vehicleRepository.findById(testVehicleId1, connection);
        assertNotNull(vehicle1, "Vehicle 1 should exist in DB");
        assertEquals("CAR-1234", vehicle1.getLicensePlate(), "License plate should match for Vehicle 1");

        // Find Vehicle 2
        Vehicle vehicle2 = vehicleRepository.findById(testVehicleId2, connection);
        assertNotNull(vehicle2, "Vehicle 2 should exist in DB");
        assertEquals("CAR-5678", vehicle2.getLicensePlate(), "License plate should match for Vehicle 2");

        // Find Vehicle 3
        Vehicle vehicle3 = vehicleRepository.findById(testVehicleId3, connection);
        assertNotNull(vehicle3, "Vehicle 3 should exist in DB");
        assertEquals("CAR-9101", vehicle3.getLicensePlate(), "License plate should match for Vehicle 3");
    }

    @Test
    @Order(5)
    @DisplayName("Test Updating Vehicles")
    void testUpdateVehicles() throws SQLException {
        // Update Vehicle 1
        Vehicle updatedVehicle1 = Vehicle.builder()
                .vehicleId(testVehicleId1)
                .licensePlate("CAR-1234")
                .model("Toyota Corolla")
                .brand("Toyota")
                .passengerCount(4)
                .color("Black") // Updated color
                .availability(false) // Updated availability
                .driverId(1)
                .pricePerKm(55.0f) // Updated price per km
                .build();
        assertTrue(vehicleRepository.update(updatedVehicle1, connection), "Vehicle 1 should be updated successfully");

        // Update Vehicle 2
        Vehicle updatedVehicle2 = Vehicle.builder()
                .vehicleId(testVehicleId2)
                .licensePlate("CAR-5678")
                .model("Suzuki Alto")
                .brand("Suzuki")
                .passengerCount(4)
                .color("Green") // Updated color
                .availability(false) // Updated availability
                .driverId(2)
                .pricePerKm(45.0f) // Updated price per km
                .build();
        assertTrue(vehicleRepository.update(updatedVehicle2, connection), "Vehicle 2 should be updated successfully");

        // Update Vehicle 3
        Vehicle updatedVehicle3 = Vehicle.builder()
                .vehicleId(testVehicleId3)
                .licensePlate("CAR-9101")
                .model("Nissan Sunny")
                .brand("Nissan")
                .passengerCount(4)
                .color("Yellow") // Updated color
                .availability(false) // Updated availability
                .driverId(3)
                .pricePerKm(65.0f) // Updated price per km
                .build();
        assertTrue(vehicleRepository.update(updatedVehicle3, connection), "Vehicle 3 should be updated successfully");

        // Verify updates
        Vehicle vehicle1 = vehicleRepository.findById(testVehicleId1, connection);
        assertEquals("Black", vehicle1.getColor(), "Color should be updated for Vehicle 1");
        assertEquals(55.0f, vehicle1.getPricePerKm(), "Price per km should be updated for Vehicle 1");

        Vehicle vehicle2 = vehicleRepository.findById(testVehicleId2, connection);
        assertEquals("Green", vehicle2.getColor(), "Color should be updated for Vehicle 2");
        assertEquals(45.0f, vehicle2.getPricePerKm(), "Price per km should be updated for Vehicle 2");

        Vehicle vehicle3 = vehicleRepository.findById(testVehicleId3, connection);
        assertEquals("Yellow", vehicle3.getColor(), "Color should be updated for Vehicle 3");
        assertEquals(65.0f, vehicle3.getPricePerKm(), "Price per km should be updated for Vehicle 3");
    }

    @Test
    @Order(6)
    @DisplayName("Test Deleting Vehicles")
    void testDeleteVehicles() throws SQLException {
        // Delete Vehicle 1
        assertTrue(vehicleRepository.delete(testVehicleId1, connection), "Vehicle 1 should be deleted successfully");
        assertNull(vehicleRepository.findById(testVehicleId1, connection), "Vehicle 1 should not exist in DB after deletion");

        // Delete Vehicle 2
        assertTrue(vehicleRepository.delete(testVehicleId2, connection), "Vehicle 2 should be deleted successfully");
        assertNull(vehicleRepository.findById(testVehicleId2, connection), "Vehicle 2 should not exist in DB after deletion");

        // Delete Vehicle 3
        assertTrue(vehicleRepository.delete(testVehicleId3, connection), "Vehicle 3 should be deleted successfully");
        assertNull(vehicleRepository.findById(testVehicleId3, connection), "Vehicle 3 should not exist in DB after deletion");
    }

    @Test
    @Order(7)
    @DisplayName("Test Getting Vehicle Count")
    void testGetVehicleCount() throws SQLException {
        Integer count = vehicleRepository.getCount(connection);
        assertNotNull(count, "Vehicle count should not be null");
        assertTrue(count >= 0, "Vehicle count should be non-negative");
    }

    @Test
    @Order(8)
    @DisplayName("Test Checking if Vehicles Exist by ID")
    void testExistsById() throws SQLException {
        assertTrue(vehicleRepository.existsById(connection, testVehicleId1), "Vehicle 1 should exist in DB");
        assertTrue(vehicleRepository.existsById(connection, testVehicleId2), "Vehicle 2 should exist in DB");
        assertTrue(vehicleRepository.existsById(connection, testVehicleId3), "Vehicle 3 should exist in DB");
    }

    @Test
    @Order(9)
    @DisplayName("Test Checking if Vehicles Exist by License Plate")
    void testExistsByLicensePlate() throws SQLException {
        assertTrue(vehicleRepository.existsByLicensePlate("CAR-1234", connection), "Vehicle 1 license plate should exist in DB");
        assertTrue(vehicleRepository.existsByLicensePlate("CAR-5678", connection), "Vehicle 2 license plate should exist in DB");
        assertTrue(vehicleRepository.existsByLicensePlate("CAR-9101", connection), "Vehicle 3 license plate should exist in DB");
    }

    @Test
    @Order(10)
    @DisplayName("Test Checking if Vehicles Exist by License Plate Except ID")
    void testExistsByLicensePlateExceptId() throws SQLException {
        assertFalse(vehicleRepository.existsByLicensePlateExceptId("CAR-1234", testVehicleId1, connection), "No other vehicle should have the same license plate as Vehicle 1");
        assertFalse(vehicleRepository.existsByLicensePlateExceptId("CAR-5678", testVehicleId2, connection), "No other vehicle should have the same license plate as Vehicle 2");
        assertFalse(vehicleRepository.existsByLicensePlateExceptId("CAR-9101", testVehicleId3, connection), "No other vehicle should have the same license plate as Vehicle 3");
    }
}