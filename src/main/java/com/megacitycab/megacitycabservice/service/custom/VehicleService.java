package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.dto.VehicleDTO;
import com.megacitycab.megacitycabservice.service.Service;

import java.util.List;

public interface VehicleService extends Service {

    List<VehicleDTO> getAllVehiclesWithDriver();

    boolean saveVehicle(VehicleDTO vehicleDTO);

    boolean deleteVehicle(Integer id);

    boolean updateVehicle(VehicleDTO build);
}
