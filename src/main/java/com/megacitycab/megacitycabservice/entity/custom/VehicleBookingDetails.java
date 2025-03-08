package com.megacitycab.megacitycabservice.entity.custom;

import com.megacitycab.megacitycabservice.entity.Entity;

import java.sql.Date;

public class VehicleBookingDetails implements Entity {
    private Integer vehicleBookingDetailsId;
    private Integer bookingId;
    private Integer vehicleId;
    private Date createdAt;
    private Date updatedAt;
    private Boolean deleted;

    public void setVehicleBookingDetailsId(Integer vehicleBookingDetailsId) {
        this.vehicleBookingDetailsId = vehicleBookingDetailsId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public VehicleBookingDetails() {
    }

    // Private constructor for Builder
    private VehicleBookingDetails(Builder builder) {
        this.vehicleBookingDetailsId = builder.vehicleBookingDetailsId;
        this.bookingId = builder.bookingId;
        this.vehicleId = builder.vehicleId;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deleted = builder.deleted;
    }

    // Getters
    public Integer getVehicleBookingDetailsId() { return vehicleBookingDetailsId; }
    public Integer getBookingId() { return bookingId; }
    public Integer getVehicleId() { return vehicleId; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public Boolean getDeleted() { return deleted; }

    // Builder Pattern Implementation
    public static class Builder {
        private Integer vehicleBookingDetailsId;
        private Integer bookingId;
        private Integer vehicleId;
        private Date createdAt;
        private Date updatedAt;
        private Boolean deleted;

        public Builder vehicleBookingDetailsId(Integer vehicleBookingDetailsId) {
            this.vehicleBookingDetailsId = vehicleBookingDetailsId;
            return this;
        }
        public Builder bookingId(Integer bookingId) {
            this.bookingId = bookingId;
            return this;
        }
        public Builder vehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }
        public Builder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Builder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        public Builder deleted(Boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public VehicleBookingDetails build() {
            return new VehicleBookingDetails(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
