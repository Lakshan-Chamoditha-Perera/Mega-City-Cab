package com.megacitycab.megacitycabservice.dto;


import java.sql.Date;

public class VehicleDTO implements DTO {
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
    private String driverName;
    private float pricePerKm;

    private VehicleDTO(VehicleDTO.Builder builder) {
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
        this.driverName = builder.driverName;
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

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(float pricePerKm) {
        this.pricePerKm = pricePerKm;
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
        private String driverName;
        private float pricePerKm;

        public VehicleDTO.Builder vehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public VehicleDTO.Builder licensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public VehicleDTO.Builder model(String model) {
            this.model = model;
            return this;
        }

        public VehicleDTO.Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public VehicleDTO.Builder passengerCount(int passengerCount) {
            this.passengerCount = passengerCount;
            return this;
        }

        public VehicleDTO.Builder color(String color) {
            this.color = color;
            return this;
        }

        public VehicleDTO.Builder availability(boolean availability) {
            this.availability = availability;
            return this;
        }

        public VehicleDTO.Builder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public VehicleDTO.Builder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public VehicleDTO.Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public VehicleDTO.Builder addedUserId(int addedUserId) {
            this.addedUserId = addedUserId;
            return this;
        }

        public VehicleDTO.Builder driverId(Integer driverId) {
            this.driverId = driverId;
            return this;
        }

        public VehicleDTO.Builder driverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        public VehicleDTO.Builder pricePerKm(float pricePerKm) {
            this.pricePerKm = pricePerKm;
            return this;
        }

        public VehicleDTO build() {
            return new VehicleDTO(this);
        }
    }

    public static VehicleDTO.Builder builder() {
        return new VehicleDTO.Builder();
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
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
                ", driverName='" + driverName + '\'' +
                ", pricePerKm=" + pricePerKm +
                '}';
    }
}
