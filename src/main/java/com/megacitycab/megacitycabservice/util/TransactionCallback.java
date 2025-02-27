package com.megacitycab.megacitycabservice.util;

import com.megacitycab.megacitycabservice.exception.MegaCityCabException;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback<T> {
    T execute(Connection connection) throws SQLException, MegaCityCabException;
}