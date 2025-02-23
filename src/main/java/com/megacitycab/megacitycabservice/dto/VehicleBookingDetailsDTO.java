package com.megacitycab.megacitycabservice.dto;

public class VehicleBookingDetailsDTO implements DTO {
    private Integer vehicleId;
    private Integer bookingId;

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "VehicleBookingDetailsDTO{" +
                "vehicleId=" + vehicleId +
                ", bookingId=" + bookingId +
                '}';
    }
}
