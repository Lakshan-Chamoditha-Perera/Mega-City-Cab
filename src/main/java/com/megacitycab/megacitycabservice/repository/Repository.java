package com.megacitycab.megacitycabservice.repository;


import com.megacitycab.megacitycabservice.entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T extends Entity, K> {
    T save(T entity, Connection connection) throws SQLException;

    List<T> findAll(Connection connection) throws SQLException;

    T findById(K id, Connection connection) throws SQLException;

    boolean delete(K id, Connection connection) throws SQLException;
}
