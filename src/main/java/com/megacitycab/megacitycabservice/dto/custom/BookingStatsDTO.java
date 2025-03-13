package com.megacitycab.megacitycabservice.dto.custom;

import com.megacitycab.megacitycabservice.dto.DTO;

public class BookingStatsDTO implements DTO {
    private Integer pendingBookings;
    private Integer cancelledBookings;
    private Integer confirmedBookings;
    private Integer totalBookings;
    private Double totalRevenue;

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Integer totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Integer getPendingBookings() {
        return pendingBookings;
    }

    public void setPendingBookings(Integer pendingBookings) {
        this.pendingBookings = pendingBookings;
    }

    public Integer getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(Integer cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public Integer getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(Integer confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    @Override
    public String toString() {
        return "BookingStatsDTO{" +
                "pendingBookings=" + pendingBookings +
                ", cancelledBookings=" + cancelledBookings +
                ", confirmedBookings=" + confirmedBookings +
                ", totalBookings=" + totalBookings +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
