package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.custom.CustomerDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.Service;

import java.util.List;

public interface CustomerService extends Service {

    List<CustomerDTO> getAllCustomers() throws RuntimeException, MegaCityCabException;

    Boolean saveCustomer(CustomerDTO customerDTO) throws RuntimeException, MegaCityCabException;

    Boolean deleteCustomer(Integer id) throws RuntimeException, MegaCityCabException;

    Boolean updateCustomer(CustomerDTO customerDTO) throws RuntimeException, MegaCityCabException;

    Integer getCustomersCount() throws RuntimeException, MegaCityCabException;

}
