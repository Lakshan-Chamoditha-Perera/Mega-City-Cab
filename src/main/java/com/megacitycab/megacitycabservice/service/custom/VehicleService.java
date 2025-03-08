package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.Service;

import java.util.List;

public interface VehicleService extends Service {

    List<VehicleDTO> getAllVehiclesWithDriver() throws MegaCityCabException, RuntimeException;

    boolean saveVehicle(VehicleDTO vehicleDTO) throws MegaCityCabException, RuntimeException;

    boolean deleteVehicle(Integer id) throws MegaCityCabException, RuntimeException;

    boolean updateVehicle(VehicleDTO build) throws MegaCityCabException, RuntimeException;

    Integer getVehiclesCount() throws MegaCityCabException, RuntimeException;

}
