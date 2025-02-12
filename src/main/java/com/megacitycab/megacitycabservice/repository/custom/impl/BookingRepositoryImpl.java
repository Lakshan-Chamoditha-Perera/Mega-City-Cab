package com.megacitycab.megacitycabservice.repository.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.Booking;
import com.megacitycab.megacitycabservice.repository.custom.BookingRepository;

import java.sql.Connection;
import java.util.List;

public class BookingRepositoryImpl implements BookingRepository {


    @Override
    public Booking save(Booking entity, Connection connection) {
        return null;
    }

    @Override
    public List<Booking> findAll(Connection connection) {
        return List.of();
    }

    @Override
    public Booking findById(Integer id, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        return false;
    }
}
