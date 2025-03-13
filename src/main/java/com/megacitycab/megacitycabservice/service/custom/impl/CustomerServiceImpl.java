package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.custom.CustomerDTO;
import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.util.RegExPatterns;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.util.List;
import java.util.regex.Pattern;

public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final TransactionManager transactionManager;

    public CustomerServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        customerRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.CUSTOMER);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws RuntimeException, MegaCityCabException {
        List<Customer> customerList = transactionManager.doReadOnly(
                connection -> customerRepository.findAll(connection));

        return customerList.stream().map(
                        customer -> CustomerDTO.builder()
                                .customerId(customer.getCustomerId())
                                .firstName(customer.getFirstName())
                                .lastName(customer.getLastName())
                                .address(customer.getAddress())
                                .nic(customer.getNic())
                                .dateOfBirth(customer.getDateOfBirth())
                                .mobileNo(customer.getMobileNo())
                                .email(customer.getEmail())
                                .build())
                .toList();

    }

    @Override
    public Boolean saveCustomer(CustomerDTO customerDTO) throws RuntimeException, MegaCityCabException {
        validateCustomerFields(customerDTO);

        Boolean isCustomerExistsByEmail = transactionManager.doReadOnly(
                connection -> customerRepository.existsByEmail(customerDTO.getEmail(), connection)
        );

        if (isCustomerExistsByEmail)
            throw new MegaCityCabException(ErrorMessage.CUSTOMER_ALREADY_EXISTS);

        Boolean isMobileNumberAvailable = transactionManager.doReadOnly(connection ->
                customerRepository.existsByMobileNumber(
                        customerDTO.getMobileNo(),
                        connection
                ));

        if (isMobileNumberAvailable) {
            throw new MegaCityCabException(ErrorMessage.CUSTOMER_MOBILE_NUMBER_ALREADY_EXISTS);
        }

        return transactionManager.doInTransaction(
                connection -> customerRepository.save(
                        Customer.builder()
                                .firstName(customerDTO.getFirstName())
                                .lastName(customerDTO.getLastName())
                                .address(customerDTO.getAddress())
                                .nic(customerDTO.getNic())
                                .dateOfBirth(customerDTO.getDateOfBirth())
                                .mobileNo(customerDTO.getMobileNo())
                                .email(customerDTO.getEmail())
                                .addedUserId(customerDTO.getAddedUserId())
                                .build(),
                        connection
                ));
    }

    private void validateCustomerFields(CustomerDTO customerDTO) throws MegaCityCabException {
        if (!Pattern.matches(RegExPatterns.NAME, customerDTO.getFirstName())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_FIRST_NAME);
        }
        if (!Pattern.matches(RegExPatterns.NAME, customerDTO.getLastName())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_LAST_NAME);
        }
        if (!Pattern.matches(RegExPatterns.ADDRESS, customerDTO.getAddress())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_ADDRESS);
        }
        if (!Pattern.matches(RegExPatterns.NIC, customerDTO.getNic())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_NIC);
        }
        if (!Pattern.matches(RegExPatterns.DATE_OF_BIRTH, customerDTO.getDateOfBirth().toString())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_DATE_OF_BIRTH);
        }
        if (!Pattern.matches(RegExPatterns.MOBILE_NUMBER, customerDTO.getMobileNo())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_MOBILE_NUMBER);
        }
        if (!Pattern.matches(RegExPatterns.EMAIL, customerDTO.getEmail())) {
            throw new MegaCityCabException(ErrorMessage.INVALID_EMAIL);
        }
    }

    @Override
    public Boolean deleteCustomer(Integer id) throws RuntimeException, MegaCityCabException {
        Boolean existsById = transactionManager.doReadOnly(
                connection -> customerRepository.existsById(id, connection));
        if (!existsById) {
            throw new MegaCityCabException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }
        return transactionManager.doInTransaction(
                connection -> customerRepository.delete(id, connection));
    }

    @Override
    public Boolean updateCustomer(CustomerDTO customerDTO) throws RuntimeException, MegaCityCabException {

        validateCustomerFields(customerDTO);

        Boolean existsById = transactionManager.doReadOnly(connection -> {
            return customerRepository.existsById(customerDTO.getCustomerId(), connection);
        });

        if (!existsById) {
            throw new MegaCityCabException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }

        Boolean existsByEmailExceptsId = transactionManager.doReadOnly(
                connection -> customerRepository.existsByEmailExceptId(
                        customerDTO.getEmail(), customerDTO.getCustomerId(), connection
                )
        );

        if (existsByEmailExceptsId) {
            throw new MegaCityCabException(ErrorMessage.CUSTOMER_ALREADY_EXISTS_FOR_EMAIL);
        }

        Customer build = Customer.builder()
                .customerId(customerDTO.getCustomerId())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .address(customerDTO.getAddress())
                .nic(customerDTO.getNic())
                .dateOfBirth(customerDTO.getDateOfBirth())
                .mobileNo(customerDTO.getMobileNo())
                .email(customerDTO.getEmail())
                .build();

        return transactionManager.doInTransaction(
                connection -> customerRepository.updateById(build, connection)
        );
    }

    @Override
    public Integer getCustomersCount() throws MegaCityCabException, RuntimeException {
        return transactionManager.doReadOnly(
                connection -> customerRepository.getCount(connection)
        );
    }
}
