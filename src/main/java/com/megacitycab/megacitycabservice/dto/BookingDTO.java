package com.megacitycab.megacitycabservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO implements DTO {
    private  int bookingId;
    private  int customerId;
    private  Integer driverId;
    private  Integer vehicleId;
    private  String pickupLocation;
    private  String destination;
    private  LocalDateTime pickupTime;
    private  String status;
    private  double distance;
    private  double fare;
    private  double discount;
    private  double tax;
    private  LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  boolean deleted;
    private  int addedUserId;
    private  List<VehicleBookingDetailsDTO> vehicleBookingDetailsDTOSList;
    private String customerName;
    private double total;

    private BookingDTO(Builder builder) {
        this.bookingId = builder.bookingId;
        this.customerId = builder.customerId;
        this.driverId = builder.driverId;
        this.vehicleId = builder.vehicleId;
        this.pickupLocation = builder.pickupLocation;
        this.destination = builder.destination;
        this.pickupTime = builder.pickupTime;
        this.status = builder.status;
        this.distance = builder.distance;
        this.fare = builder.fare;
        this.discount = builder.discount;
        this.tax = builder.tax;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deleted = builder.deleted;
        this.addedUserId = builder.addedUserId;
        this.vehicleBookingDetailsDTOSList = builder.vehicleBookingDetailsDTOSList;
        this.customerName = builder.customerName;
        this.total = builder.total;
    }

    // Builder Pattern Implementation
    public static class Builder {
        private int bookingId;
        private int customerId;
        private Integer driverId;
        private Integer vehicleId;
        private String pickupLocation;
        private String destination;
        private LocalDateTime pickupTime;
        private String status;
        private double distance;
        private double fare;
        private double discount;
        private double tax;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean deleted;
        private int addedUserId;
        private List<VehicleBookingDetailsDTO> vehicleBookingDetailsDTOSList;
        private String customerName;
        private double total;

        public Builder bookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder customerId(int customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder driverId(Integer driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder vehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder pickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder pickupTime(LocalDateTime pickupTime) {
            this.pickupTime = pickupTime;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder distance(double distance) {
            this.distance = distance;
            return this;
        }

        public Builder fare(double fare) {
            this.fare = fare;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Builder tax(double tax) {
            this.tax = tax;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
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

        public Builder vehicleBookingDetailsDTOSList(List<VehicleBookingDetailsDTO> vehicleBookingDetailsDTOSList) {
            this.vehicleBookingDetailsDTOSList = vehicleBookingDetailsDTOSList;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder total(double total) {
            this.total = total;
            return this;
        }

        public BookingDTO build() {
            return new BookingDTO(this);
        }
    }

    // Static method to create a new Builder instance
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public String getStatus() {
        return status;
    }

    public double getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTax() {
        return tax;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public int getAddedUserId() {
        return addedUserId;
    }

    public List<VehicleBookingDetailsDTO> getVehicleBookingDetailsDTOSList() {
        return vehicleBookingDetailsDTOSList;
    }

    public String getCustomerName() {
        return customerName;
    }

    // Setter for customerName (if needed)
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setAddedUserId(int addedUserId) {
        this.addedUserId = addedUserId;
    }

    public void setVehicleBookingDetailsDTOSList(List<VehicleBookingDetailsDTO> vehicleBookingDetailsDTOSList) {
        this.vehicleBookingDetailsDTOSList = vehicleBookingDetailsDTOSList;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // toString() method for debugging and logging
    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", driverId=" + driverId +
                ", vehicleId=" + vehicleId +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupTime=" + pickupTime +
                ", status='" + status + '\'' +
                ", distance=" + distance +
                ", fare=" + fare +
                ", discount=" + discount +
                ", tax=" + tax +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deleted=" + deleted +
                ", addedUserId=" + addedUserId +
                ", vehicleBookingDetailsDTOSList=" + vehicleBookingDetailsDTOSList +
                ", customerName='" + customerName + '\'' +
                ", total=" + total +
                '}';
    }
}