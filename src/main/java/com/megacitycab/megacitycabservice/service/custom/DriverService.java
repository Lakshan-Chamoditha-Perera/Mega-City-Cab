package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.DriverDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.Service;

import java.util.List;

public interface DriverService extends Service {

    List<DriverDTO> getAllAvailableDriversForVehicle() throws MegaCityCabException;

    Boolean saveDriver(DriverDTO driverDTO) throws RuntimeException, MegaCityCabException;

    Boolean deleteDriver(Integer id) throws RuntimeException, MegaCityCabException;

    Boolean updateDriver(DriverDTO driverDTO) throws RuntimeException, MegaCityCabException;

    List<DriverDTO> getAllDrivers() throws MegaCityCabException, RuntimeException;

    Integer getDriversCount() throws MegaCityCabException, RuntimeException;
}
