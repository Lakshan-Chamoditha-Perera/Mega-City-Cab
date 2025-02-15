package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.CustomerDTO;
import com.megacitycab.megacitycabservice.service.Service;

import java.io.IOException;
import java.util.List;

public interface CustomerService extends Service {
    List<CustomerDTO> getAllCustomers();

    Boolean saveCustomer(CustomerDTO customerDTO) throws IOException;

    Boolean deleteCustomer(Integer id) throws IOException;

    Boolean updateCustomer(CustomerDTO customerDTO);
}
