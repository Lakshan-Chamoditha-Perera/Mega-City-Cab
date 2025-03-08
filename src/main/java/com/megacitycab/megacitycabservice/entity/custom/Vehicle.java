package com.megacitycab.megacitycabservice.entity.custom;

import com.megacitycab.megacitycabservice.entity.Entity;

import java.io.Serializable;
import java.sql.Date;

public class Vehicle implements Entity {
    private int vehicleId;
    private String licensePlate;
    private String model;
    private String brand;
    private int passengerCount;
    private String color;
    private boolean availability;
    private Date createdAt;
    private Date updatedAt;
    private boolean deleted;
    private int addedUserId;
    private Integer driverId;
    private float pricePerKm;

    private Vehicle(Builder builder) {
        this.vehicleId = builder.vehicleId;
        this.licensePlate = builder.licensePlate;
        this.model = builder.model;
        this.brand = builder.brand;
        this.passengerCount = builder.passengerCount;
        this.color = builder.color;
        this.availability = builder.availability;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deleted = builder.deleted;
        this.addedUserId = builder.addedUserId;
        this.driverId = builder.driverId;
        this.pricePerKm = builder.pricePerKm;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getAddedUserId() {
        return addedUserId;
    }

    public void setAddedUserId(int addedUserId) {
        this.addedUserId = addedUserId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public static class Builder {
        private int vehicleId;
        private String licensePlate;
        private String model;
        private String brand;
        private int passengerCount;
        private String color;
        private boolean availability;
        private Date createdAt;
        private Date updatedAt;
        private boolean deleted;
        private int addedUserId;
        private Integer driverId;
        private float pricePerKm;

        public Builder vehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder licensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder passengerCount(int passengerCount) {
            this.passengerCount = passengerCount;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder availability(boolean availability) {
            this.availability = availability;
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

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder addedUserId(int addedUserId) {
            this.addedUserId = addedUserId;
            return this;
        }

        public Builder driverId(Integer driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder pricePerKm(float pricePerKm) {
            this.pricePerKm = pricePerKm;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", passengerCount=" + passengerCount +
                ", color='" + color + '\'' +
                ", availability=" + availability +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deleted=" + deleted +
                ", addedUserId=" + addedUserId +
                ", driverId=" + driverId +
                ", pricePerKm=" + pricePerKm +
                '}';
    }
}