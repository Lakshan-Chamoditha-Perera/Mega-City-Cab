package com.megacitycab.megacitycabservice.repository.custom;

import com.megacitycab.megacitycabservice.entity.custom.Driver;
import com.megacitycab.megacitycabservice.repository.Repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface DriverRepository extends Repository<Driver, Integer> {

    Boolean updateById(Driver driver, Connection connection) throws SQLException;

}
