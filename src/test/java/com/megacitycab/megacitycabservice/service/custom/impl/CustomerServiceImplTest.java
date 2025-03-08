package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.CustomerDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.util.TransactionManager;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceImplTest {

    private static TransactionManager transactionManager;
    private static CustomerService customerService;
    private static Integer testCustomerId;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        // Initialize transaction manager and service
        transactionManager = new TransactionManager(); // Ensure this connects to your test DB
        customerService = new CustomerServiceImpl(transactionManager);
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving a New Customer")
    void testSaveCustomer() throws MegaCityCabException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Alice")
                .lastName("Smith")
                .address("123 Main St")
                .nic("123456789V")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .mobileNo("0771234567")
                .email("alice.smith@example.com")
                .build();

        Boolean result = customerService.saveCustomer(customerDTO);
        assertTrue(result, "Customer should be saved successfully");

        // Fetch the customer to get ID for further tests
        List<CustomerDTO> customers = customerService.getAllCustomers();
        testCustomerId = customers.stream()
                .filter(customer -> customer.getEmail().equals("alice.smith@example.com"))
                .findFirst()
                .map(CustomerDTO::getCustomerId)
                .orElse(null);

        assertNotNull(testCustomerId, "Saved customer should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Fetching All Customers")
    void testGetAllCustomers() throws MegaCityCabException {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertFalse(customers.isEmpty(), "There should be at least one customer");
    }

    @Test
    @Order(3)
    @DisplayName("Test Updating Customer")
    void testUpdateCustomer() throws MegaCityCabException {
        assertNotNull(testCustomerId, "Test customer ID should not be null");

        CustomerDTO updatedCustomer = CustomerDTO.builder()
                .customerId(testCustomerId)
                .firstName("Alice")
                .lastName("Johnson")
                .address("456 Elm St")
                .nic("987654321V")
                .dateOfBirth(Date.valueOf(String.valueOf("1990-01-01")))
                .mobileNo("0777654321")
                .email("alice.johnson@example.com")
                .build();

        Boolean result = customerService.updateCustomer(updatedCustomer);
        assertTrue(result, "Customer should be updated successfully");

        // Fetch and verify update
        List<CustomerDTO> customers = customerService.getAllCustomers();
        boolean isUpdated = customers.stream()
                .anyMatch(customer -> customer.getCustomerId() == testCustomerId
                        && customer.getEmail().equals("alice.johnson@example.com"));

        assertTrue(isUpdated, "Updated customer should exist in DB");
    }

    @Test
    @Order(4)
    @DisplayName("Test Deleting a Customer")
    void testDeleteCustomer() throws MegaCityCabException {
        assertNotNull(testCustomerId, "Test customer ID should not be null");

        Boolean result = customerService.deleteCustomer(testCustomerId);
        assertTrue(result, "Customer should be deleted successfully");

        // Verify deletion
        List<CustomerDTO> customers = customerService.getAllCustomers();
        boolean exists = customers.stream().anyMatch(customer -> customer.getCustomerId() == testCustomerId);

        assertFalse(exists, "Deleted customer should not exist in DB");
    }

    @Test
    @Order(5)
    @DisplayName("Test Get Customers Count")
    void testGetCustomersCount() throws MegaCityCabException {
        Integer count = customerService.getCustomersCount();
        assertNotNull(count, "Customer count should not be null");
        assertTrue(count >= 0, "Customer count should be non-negative");
    }
}