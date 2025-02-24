package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.dto.CustomerDTO;
import com.megacitycab.megacitycabservice.entity.custom.Customer;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.CustomerRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.io.IOException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final TransactionManager transactionManager;

    public CustomerServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        customerRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.CUSTOMER);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        try {
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
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public Boolean saveCustomer(CustomerDTO customerDTO) throws RuntimeException {
        try {
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
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteCustomer(Integer id) throws IOException {
        try {
            return transactionManager.doInTransaction(
                    connection -> customerRepository.delete(id, connection));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean updateCustomer(CustomerDTO customerDTO) {
        try {
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
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
