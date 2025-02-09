package com.megacitycab.megacitycabservice.repository;


import com.megacitycab.megacitycabservice.entity.Entity;

import java.util.List;

public interface Repository<T extends Entity, K> {
    T save(T entity);

    List<T> findAll();

    T findById(K id);

    boolean delete(K id);
}
