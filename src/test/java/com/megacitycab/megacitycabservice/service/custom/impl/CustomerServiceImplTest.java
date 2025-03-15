package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.CustomerDTO;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.util.TransactionManager;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceImplTest {

    private static TransactionManager transactionManager;
    private static CustomerService customerService;
    private static Integer testCustomerId1;
    private static Integer testCustomerId2;
    private static Integer testCustomerId3;
    private static Integer testCustomerId4;
    private static Integer testCustomerId5;

    @BeforeAll
    static void setUp() throws Exception {
        transactionManager = new TransactionManager();
        customerService = new CustomerServiceImpl(transactionManager);
    }

    @Test
    @Order(1)
    @DisplayName("Test Saving Customer 1 (Ranil Wickremesinghe)")
    void testSaveCustomer1() throws MegaCityCabException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Ranil")
                .lastName("Wickremesinghe")
                .address("Temple Trees, Colombo")
                .nic("123456789V")
                .dateOfBirth(Date.valueOf("1949-03-24"))
                .mobileNo("0771234567")
                .email("ranil.w@example.com")
                .addedUserId(1)
                .build();

        Boolean result = customerService.saveCustomer(customerDTO);
        assertTrue(result, "Customer Ranil Wickremesinghe should be saved successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        testCustomerId1 = customers.stream()
                .filter(customer -> customer.getEmail().equals("ranil.w@example.com"))
                .findFirst()
                .map(CustomerDTO::getCustomerId)
                .orElse(null);

        assertNotNull(testCustomerId1, "Saved customer Ranil Wickremesinghe should exist in DB");
    }

    @Test
    @Order(2)
    @DisplayName("Test Saving Customer with Invalid First Name")
    void testSaveCustomerWithInvalidFirstName() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Ranil123")
                .lastName("Wickremesinghe")
                .address("Temple Trees, Colombo")
                .nic("123456789V")
                .dateOfBirth(Date.valueOf("1949-03-24"))
                .mobileNo("0771234567")
                .email("ranil.w@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> customerService.saveCustomer(customerDTO)
        );

        assertEquals(ErrorMessage.INVALID_FIRST_NAME.getMessage(), exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test Saving Customer with Invalid NIC")
    void testSaveCustomerWithInvalidNIC() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Ranil")
                .lastName("Wickremesinghe")
                .address("Temple Trees, Colombo")
                .nic("12345678")
                .dateOfBirth(Date.valueOf("1949-03-24"))
                .mobileNo("0771234567")
                .email("ranil.w@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> customerService.saveCustomer(customerDTO)
        );

        assertEquals(ErrorMessage.INVALID_NIC.getMessage(), exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test Saving Customer with Invalid Mobile Number")
    void testSaveCustomerWithInvalidMobileNumber() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Ranil")
                .lastName("Wickremesinghe")
                .address("Temple Trees, Colombo")
                .nic("123456789V")
                .dateOfBirth(Date.valueOf("1949-03-24"))
                .mobileNo("077123456")
                .email("ranil.w@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(
                MegaCityCabException.class,
                () -> customerService.saveCustomer(customerDTO)
        );

        assertEquals(ErrorMessage.INVALID_MOBILE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Test Saving Customer 2 (Mahinda Rajapaksa)")
    void testSaveCustomer2() throws MegaCityCabException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Mahinda")
                .lastName("Rajapaksa")
                .address("Medamulana, Hambantota")
                .nic("987654321V")
                .dateOfBirth(Date.valueOf("1945-11-18"))
                .mobileNo("0779876543")
                .email("mahinda.r@example.com")
                .addedUserId(1)
                .build();

        Boolean result = customerService.saveCustomer(customerDTO);
        assertTrue(result, "Customer Mahinda Rajapaksa should be saved successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        testCustomerId2 = customers.stream()
                .filter(customer -> customer.getEmail().equals("mahinda.r@example.com"))
                .findFirst()
                .map(CustomerDTO::getCustomerId)
                .orElse(null);

        assertNotNull(testCustomerId2, "Saved customer Mahinda Rajapaksa should exist in DB");
    }

    @Test
    @Order(6)
    @DisplayName("Test Saving Customer 3 (Sajith Premadasa)")
    void testSaveCustomer3() throws MegaCityCabException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Sajith")
                .lastName("Premadasa")
                .address("No. 120/4, Wijerama Mawatha, Colombo")
                .nic("456789123V")
                .dateOfBirth(Date.valueOf("1967-01-12"))
                .mobileNo("0771111111")
                .email("sajith.p@example.com")
                .addedUserId(1)
                .build();

        Boolean result = customerService.saveCustomer(customerDTO);
        assertTrue(result, "Customer Sajith Premadasa should be saved successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        testCustomerId3 = customers.stream()
                .filter(customer -> customer.getEmail().equals("sajith.p@example.com"))
                .findFirst()
                .map(CustomerDTO::getCustomerId)
                .orElse(null);

        assertNotNull(testCustomerId3, "Saved customer Sajith Premadasa should exist in DB");
    }

    @Test
    @Order(7)
    @DisplayName("Test Saving Customer 4 (Anura Kumara)")
    void testSaveCustomer4() throws MegaCityCabException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Anura")
                .lastName("Kumara")
                .address("No. 45, Kotte Road, Colombo")
                .nic("789123456V")
                .dateOfBirth(Date.valueOf("1970-05-15"))
                .mobileNo("0772222222")
                .email("anura.k@example.com")
                .addedUserId(1)
                .build();

        Boolean result = customerService.saveCustomer(customerDTO);
        assertTrue(result, "Customer Anura Kumara should be saved successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        testCustomerId4 = customers.stream()
                .filter(customer -> customer.getEmail().equals("anura.k@example.com"))
                .findFirst()
                .map(CustomerDTO::getCustomerId)
                .orElse(null);

        assertNotNull(testCustomerId4, "Saved customer Anura Kumara should exist in DB");
    }

    @Test
    @Order(8)
    @DisplayName("Test Saving Customer 5 (Gotabaya Rajapaksa)")
    void testSaveCustomer5() throws MegaCityCabException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Gotabaya")
                .lastName("Rajapaksa")
                .address("Mirihana, Nugegoda")
                .nic("654321987V")
                .dateOfBirth(Date.valueOf("1949-06-20"))
                .mobileNo("0773333333")
                .email("gotabaya.r@example.com")
                .addedUserId(1)
                .build();

        Boolean result = customerService.saveCustomer(customerDTO);
        assertTrue(result, "Customer Gotabaya Rajapaksa should be saved successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        testCustomerId5 = customers.stream()
                .filter(customer -> customer.getEmail().equals("gotabaya.r@example.com"))
                .findFirst()
                .map(CustomerDTO::getCustomerId)
                .orElse(null);

        assertNotNull(testCustomerId5, "Saved customer Gotabaya Rajapaksa should exist in DB");
    }

    @Test
    @Order(9)
    @DisplayName("Test Fetching All Customers")
    void testGetAllCustomers() throws MegaCityCabException {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertFalse(customers.isEmpty(), "There should be at least five customers");
        assertEquals(5, customers.size(), "There should be exactly five customers in the DB");
    }

    @Test
    @Order(10)
    @DisplayName("Test Updating Customer 1 (Ranil Wickremesinghe)")
    void testUpdateCustomer1() throws MegaCityCabException {
        assertNotNull(1, "Test customer ID 1 should not be null");

        CustomerDTO updatedCustomer = CustomerDTO.builder()
                .customerId(1)
                .firstName("Ranil")
                .lastName("Wickremesinghe Updated")
                .address("President House, Colombo")
                .nic("123456789V")
                .dateOfBirth(Date.valueOf("1949-03-24"))
                .mobileNo("0771234567")
                .email("ranil.updated@example.com")
                .addedUserId(1)
                .build();

        Boolean result = customerService.updateCustomer(updatedCustomer);
        assertTrue(result, "Customer Ranil Wickremesinghe should be updated successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        boolean isUpdated = customers.stream()
                .anyMatch(customer -> customer.getCustomerId() == 1
                        && customer.getEmail().equals("ranil.updated@example.com"));

        assertTrue(isUpdated, "Updated customer Ranil Wickremesinghe should exist in DB");
    }

    @Test
    @Order(11)
    @DisplayName("Test Deleting Customer 2 (Mahinda Rajapaksa)")
    void testDeleteCustomer2() throws MegaCityCabException {
        assertNotNull(2, "Test customer ID 2 should not be null");

        Boolean result = customerService.deleteCustomer(2);
        assertTrue(result, "Customer Mahinda Rajapaksa should be deleted successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        boolean exists = customers.stream().anyMatch(customer -> customer.getCustomerId() == 2);

        assertFalse(exists, "Deleted customer Mahinda Rajapaksa should not exist in DB");
    }

//    @Test
//    @Order(9)
    @DisplayName("Test Get Customers Count After Deletion")
    void testGetCustomersCountAfterDeletion() throws MegaCityCabException {
        Integer count = customerService.getCustomersCount();
        assertNotNull(count, "Customer count should not be null");
        assertEquals(4, count, "Customer count should be 4 after deleting one customer");
    }

//    @Test
//    @Order(10)
    @DisplayName("Test Saving a Customer with Existing Email")
    void testSaveCustomerWithExistingEmail() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Test")
                .lastName("User")
                .address("Test Address")
                .nic("111111111V")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .mobileNo("0774444444")
                .email("ranil.updated@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(MegaCityCabException.class, () -> {
            customerService.saveCustomer(customerDTO);
        });

        assertEquals(ErrorMessage.CUSTOMER_ALREADY_EXISTS.getMessage(), exception.getMessage(),
                "Should throw exception for duplicate email");
    }

//    @Test
//    @Order(11)
    @DisplayName("Test Saving a Customer with Existing Mobile Number")
    void testSaveCustomerWithExistingMobileNumber() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Test")
                .lastName("User")
                .address("Test Address 2")
                .nic("222222222V")
                .dateOfBirth(Date.valueOf("1990-02-02"))
                .mobileNo("0771234567")
                .email("test.user2@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(MegaCityCabException.class, () -> {
            customerService.saveCustomer(customerDTO);
        });

        assertEquals(ErrorMessage.CUSTOMER_MOBILE_NUMBER_ALREADY_EXISTS.getMessage(), exception.getMessage(),
                "Should throw exception for duplicate mobile number");
    }

//    @Test
//    @Order(12)
//    @DisplayName("Test Updating a Non-Existent Customer")
    void testUpdateNonExistentCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerId(999)
                .firstName("Non")
                .lastName("Existent")
                .address("Nowhere")
                .nic("000000000V")
                .dateOfBirth(Date.valueOf("2000-01-01"))
                .mobileNo("0770000000")
                .email("non.existent@example.com")
                .addedUserId(1)
                .build();

        MegaCityCabException exception = assertThrows(MegaCityCabException.class, () -> {
            customerService.updateCustomer(customerDTO);
        });

        assertEquals(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage(), exception.getMessage(),
                "Should throw exception for non-existent customer");
    }

//    @Test
//    @Order(13)
//    @DisplayName("Test Deleting a Non-Existent Customer")
    void testDeleteNonExistentCustomer() {
        MegaCityCabException exception = assertThrows(MegaCityCabException.class, () -> {
            customerService.deleteCustomer(999);
        });

        assertEquals(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage(), exception.getMessage(),
                "Should throw exception for non-existent customer");
    }

//    @Test
//    @Order(14)
//    @DisplayName("Test Bulk Delete Customers")
    void testBulkDeleteCustomers() throws MegaCityCabException {
        assertNotNull(testCustomerId3, "Test customer ID 3 should not be null");
        assertNotNull(testCustomerId4, "Test customer ID 4 should not be null");

        customerService.deleteCustomer(testCustomerId3);
        customerService.deleteCustomer(testCustomerId4);

        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertEquals(2, customers.size(), "Should have 2 customers remaining after bulk delete");
    }

//    @Test
//    @Order(15)
//    @DisplayName("Test Updating Customer 5 (Gotabaya Rajapaksa)")
    void testUpdateCustomer5() throws MegaCityCabException {
        assertNotNull(testCustomerId5, "Test customer ID 5 should not be null");

        CustomerDTO updatedCustomer = CustomerDTO.builder()
                .customerId(testCustomerId5)
                .firstName("Gotabaya")
                .lastName("Rajapaksa Updated")
                .address("New Location, Colombo")
                .nic("654321987V")
                .dateOfBirth(Date.valueOf("1949-06-20"))
                .mobileNo("0773333333")
                .email("gotabaya.updated@example.com")
                .addedUserId(1)
                .build();

        Boolean result = customerService.updateCustomer(updatedCustomer);
        assertTrue(result, "Customer Gotabaya Rajapaksa should be updated successfully");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        boolean isUpdated = customers.stream()
                .anyMatch(customer -> customer.getCustomerId() == testCustomerId5
                        && customer.getEmail().equals("gotabaya.updated@example.com"));

        assertTrue(isUpdated, "Updated customer Gotabaya Rajapaksa should exist in DB");
    }
}