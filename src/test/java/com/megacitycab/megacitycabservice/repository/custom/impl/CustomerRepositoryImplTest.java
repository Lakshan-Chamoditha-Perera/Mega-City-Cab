package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.configuration.db.SingleDatabaseConnection;
import com.megacitycab.megacitycabservice.entity.custom.Customer;
import org.junit.jupiter.api.*;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryImplTest {

    static Connection connection;
    private static CustomerRepositoryImpl customerRepository;
    private static Integer testCustomerId = 103;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException, NamingException {
        connection = SingleDatabaseConnection.getInstance().getConnection();
        customerRepository = new CustomerRepositoryImpl();
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving a New Customer")
    void testSaveCustomer() throws SQLException {
        Customer customer = Customer.builder()
                .firstName("Kamal") // Valid first name
                .lastName("Perera") // Valid last name
                .email("kamal.perera@example.com") // Valid email
                .nic("123456789V") // Valid NIC
                .address("No. 120/4, Wijerama Mawatha, Colombo") // Valid address
                .mobileNo("0771234567") // Valid mobile number
                .dateOfBirth(Date.valueOf("1990-01-01")) // Valid date of birth
                .addedUserId(1) // Valid added user ID
                .build();

        Boolean result = customerRepository.save(customer, connection);
        assertTrue(result, "Customer should be saved successfully");

        // Fetch the customer to get ID for further tests
        List<Customer> customers = customerRepository.findAll(connection);
        testCustomerId = customers.stream()
                .filter(c -> c.getEmail().equals("kamal.perera@example.com"))
                .findFirst()
                .map(Customer::getCustomerId)
                .orElse(null);

        assertNotNull(testCustomerId, "Saved customer should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Saving Customer with Duplicate Email")
    void testSaveCustomerWithDuplicateEmail() throws SQLException {
        Customer customer = Customer.builder()
                .firstName("Nimal")
                .lastName("Fernando")
                .email("kamal.perera@example.com") // Duplicate email
                .nic("987654321V")
                .address("No. 25, Galle Road, Colombo 03")
                .mobileNo("0777654321")
                .dateOfBirth(Date.valueOf("1985-05-15"))
                .addedUserId(1)
                .build();

        SQLException exception = assertThrows(
                SQLException.class,
                () -> customerRepository.save(customer, connection)
        );

        assertTrue(exception.getMessage().contains("Duplicate entry"), "Should throw SQLException for duplicate email");
    }

    @Test
    @Order(3)
    @DisplayName("Test Fetching All Customers")
    void testFindAllCustomers() throws SQLException {
        List<Customer> customers = customerRepository.findAll(connection);
        assertFalse(customers.isEmpty(), "There should be at least one customer");
    }

    @Test
    @Order(4)
    @DisplayName("Test Finding Customer by ID")
    void testFindCustomerById() throws SQLException {
        assertNotNull(testCustomerId, "Test customer ID should not be null");

        Customer customer = customerRepository.findById(testCustomerId, connection);
        assertNotNull(customer, "Customer should exist in DB");
        assertEquals("Kamal", customer.getFirstName(), "First name should match");
        assertEquals("Perera", customer.getLastName(), "Last name should match");
    }

    @Test
    @Order(5)
    @DisplayName("Test Updating Customer")
    void testUpdateCustomer() throws SQLException {
        assertNotNull(testCustomerId, "Test customer ID should not be null");

        Customer updatedCustomer = Customer.builder()
                .customerId(testCustomerId)
                .firstName("Nimal") // Updated first name
                .lastName("Fernando") // Updated last name
                .email("nimal.fernando@example.com") // Updated email
                .nic("987654321V") // Updated NIC
                .address("No. 25, Galle Road, Colombo 03") // Updated address
                .mobileNo("0777654321") // Updated mobile number
                .dateOfBirth(Date.valueOf("1985-05-15")) // Updated date of birth
                .build();

        Boolean result = customerRepository.updateById(updatedCustomer, connection);
        assertTrue(result, "Customer should be updated successfully");

        // Fetch and verify update
        Customer customer = customerRepository.findById(testCustomerId, connection);
        assertNotNull(customer, "Updated customer should exist in DB");
        assertEquals("Nimal", customer.getFirstName(), "First name should be updated");
        assertEquals("Fernando", customer.getLastName(), "Last name should be updated");
    }

    @Test
    @Order(6)
    @DisplayName("Test Deleting a Customer")
    void testDeleteCustomer() throws SQLException {
        assertNotNull(testCustomerId, "Test customer ID should not be null");

        Boolean result = customerRepository.delete(testCustomerId, connection);
        assertTrue(result, "Customer should be deleted successfully");

        // Verify deletion
        Customer customer = customerRepository.findById(testCustomerId, connection);
        assertNull(customer, "Deleted customer should not exist in DB");
    }

    @Test
    @Order(7)
    @DisplayName("Test Getting Customer Count")
    void testGetCustomerCount() throws SQLException {
        Integer count = customerRepository.getCount(connection);
        assertNotNull(count, "Customer count should not be null");
        assertTrue(count >= 0, "Customer count should be non-negative");
    }

    @Test
    @Order(8)
    @DisplayName("Test Checking if Customer Exists by ID")
    void testExistsById() throws SQLException {
        assertNotNull(testCustomerId, "Test customer ID should not be null");

        Boolean exists = customerRepository.existsById(testCustomerId, connection);
        assertFalse(exists, "Customer should exist in DB");
    }

    @Test
    @Order(9)
    @DisplayName("Test Checking if Customer Exists by Email")
    void testExistsByEmail() throws SQLException {
        Boolean exists = customerRepository.existsByEmail("kamal.perera@example.com", connection);
        assertFalse(exists, "Customer with the given email should exist in DB");
    }

    @Test
    @Order(10)
    @DisplayName("Test Checking if Customer Exists by Mobile Number")
    void testExistsByMobileNumber() throws SQLException {
        Boolean exists = customerRepository.existsByMobileNumber("0771234567", connection);
        assertFalse(exists, "Customer with the given mobile number should exist in DB");
    }

    @Test
    @Order(11)
    @DisplayName("Test Checking if Customer Exists by Email Except ID")
    void testExistsByEmailExceptId() throws SQLException {
        assertNotNull(testCustomerId, "Test customer ID should not be null");

        Boolean exists = customerRepository.existsByEmailExceptId("kamal.perera@example.com", testCustomerId, connection);
        assertFalse(exists, "No other customer should have the same email");
    }

    @Test
    @Order(12)
    @DisplayName("Test Saving Multiple Customers")
    void testSaveMultipleCustomers() throws SQLException {
        Customer customer1 = Customer.builder()
                .firstName("Sunil")
                .lastName("Jayasinghe")
                .email("sunil.jayasinghe@example.com")
                .nic("123456789V")
                .address("No. 120/4, Wijerama Mawatha, Colombo")
                .mobileNo("0771111111")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .addedUserId(1)
                .build();

        Customer customer2 = Customer.builder()
                .firstName("Anil")
                .lastName("Gunathilake")
                .email("anil.gunathilake@example.com")
                .nic("71569874")
                .address("No. 25, Galle Road, Colombo 03")
                .mobileNo("0772222222")
                .dateOfBirth(Date.valueOf("1985-05-15"))
                .addedUserId(1)
                .build();

        Customer customer3 = Customer.builder()
                .firstName("Saman")
                .lastName("Silva")
                .email("saman.silva@example.com")
                .nic("456789123V")
                .address("No. 10, Main Street, Kandy")
                .mobileNo("0773333333")
                .dateOfBirth(Date.valueOf("1995-10-20"))
                .addedUserId(1)
                .build();

        // Save all customers
        assertTrue(customerRepository.save(customer1, connection), "Customer 1 should be saved successfully");
        assertTrue(customerRepository.save(customer2, connection), "Customer 2 should be saved successfully");
        assertTrue(customerRepository.save(customer3, connection), "Customer 3 should be saved successfully");

        // Verify all customers exist
        List<Customer> customers = customerRepository.findAll(connection);
        assertEquals(3, customers.size(), "All 3 customers should exist in DB");
    }

    @Test
    @Order(13)
    @DisplayName("Test Finding Non-Existent Customer by ID")
    void testFindNonExistentCustomerById() throws SQLException {
        Integer nonExistentCustomerId = 9999; // Non-existent customer ID
        Customer customer = customerRepository.findById(nonExistentCustomerId, connection);
        assertNull(customer, "Non-existent customer should return null");
    }

    @Test
    @Order(14)
    @DisplayName("Test Deleting Non-Existent Customer")
    void testDeleteNonExistentCustomer() throws SQLException {
        Integer nonExistentCustomerId = 9999; // Non-existent customer ID
        Boolean result = customerRepository.delete(nonExistentCustomerId, connection);
        assertFalse(result, "Deleting a non-existent customer should return false");
    }

    @Test
    @Order(15)
    @DisplayName("Test Updating Non-Existent Customer")
    void testUpdateNonExistentCustomer() throws SQLException {
        Customer nonExistentCustomer = Customer.builder()
                .customerId(9999) // Non-existent customer ID
                .firstName("NonExistent")
                .lastName("Customer")
                .email("nonexistent.customer@example.com")
                .nic("999999999V")
                .address("No. 999, Non-Existent Street, Colombo")
                .mobileNo("0779999999")
                .dateOfBirth(Date.valueOf("2000-01-01"))
                .build();

        Boolean result = customerRepository.updateById(nonExistentCustomer, connection);
        assertFalse(result, "Updating a non-existent customer should return false");
    }

    @Test
    @Order(16)
    @DisplayName("Test Checking if Non-Existent Customer Exists by ID")
    void testExistsByNonExistentId() throws SQLException {
        Integer nonExistentCustomerId = 9999; // Non-existent customer ID
        Boolean exists = customerRepository.existsById(nonExistentCustomerId, connection);
        assertFalse(exists, "Non-existent customer should not exist in DB");
    }

    @Test
    @Order(17)
    @DisplayName("Test Checking if Non-Existent Customer Exists by Email")
    void testExistsByNonExistentEmail() throws SQLException {
        String nonExistentEmail = "nonexistent.customer@example.com"; // Non-existent email
        Boolean exists = customerRepository.existsByEmail(nonExistentEmail, connection);
        assertFalse(exists, "Non-existent customer email should not exist in DB");
    }

    @Test
    @Order(18)
    @DisplayName("Test Checking if Non-Existent Customer Exists by Mobile Number")
    void testExistsByNonExistentMobileNumber() throws SQLException {
        String nonExistentMobileNo = "0779999999"; // Non-existent mobile number
        Boolean exists = customerRepository.existsByMobileNumber(nonExistentMobileNo, connection);
        assertFalse(exists, "Non-existent customer mobile number should not exist in DB");
    }
}