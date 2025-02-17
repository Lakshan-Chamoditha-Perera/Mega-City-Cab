package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.CustomerDTO;
import com.megacitycab.megacitycabservice.dto.DriverDTO;
import com.megacitycab.megacitycabservice.service.Service;

import java.io.IOException;
import java.util.List;

public interface DriverService extends Service {

    List<DriverDTO> getAllDrivers();

    Boolean saveDriver(DriverDTO driverDTO) throws IOException;

    Boolean deleteDriver(Integer id) throws IOException;

    Boolean updateDriver(DriverDTO driverDTO);

}
