package com.megacitycab.megacitycabservice.util;

public abstract class RegExPatterns {

    public static final String NAME = "^[A-Za-z ]+$";
    public static final String ADDRESS = "^[A-Za-z0-9 ,./-]+$";
    public static final String NIC = "^(\\d{9}[Vv]?|\\d{12})$";
    public static final String DATE_OF_BIRTH = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String MOBILE_NUMBER = "^\\d{10}$";
    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static final String LICENSE_PLATE = "^[A-Z]{2,3}-[0-9]{4}$";
    public static final String MODEL = "^[A-Za-z0-9 ]+$";
    public static final String BRAND = "^[A-Za-z ]+$";
    public static final String COLOR = "^[A-Za-z]+$";
    public static final String DRIVER_NAME = "^[A-Za-z ]+$";
    public static final String DRIVER_LICENSE_NUMBER = "^[A-Za-z0-9]{8,15}$";

    public static final String POSITIVE_INTEGER = "^[1-9]\\d*$";
    public static final String POSITIVE_FLOAT = "^[1-9]\\d*(\\.\\d+)?$";
}
