package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.CustomerDTO;
import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.exception.ErrorMessage;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.util.List;

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

        Boolean isCustomerExistsByEmail = transactionManager.doReadOnly(
                connection -> customerRepository.existsByEmail(customerDTO.getEmail(), connection)
        );

        if (isCustomerExistsByEmail) {
            throw new MegaCityCabException(ErrorMessage.CUSTOMER_ALREADY_EXISTS);
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
                                .build(),
                        connection
                ));
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

        Boolean existsById = transactionManager.doReadOnly(connection -> {
            return customerRepository.existsById(customerDTO.getCustomerId(), connection);
        });

        if (!existsById) {
            throw new MegaCityCabException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }

        //  check customer email excepts id
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
