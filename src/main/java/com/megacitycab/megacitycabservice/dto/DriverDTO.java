package com.megacitycab.megacitycabservice.dto;

import java.sql.Date;

public class DriverDTO implements DTO {
    private int driverId;
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private String mobileNo;
    private String email;
    private boolean isAvailable;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted;
    private int addedUserId;

    public static Builder builder() {
        return new Builder();
    }

    // Private constructor to enforce the use of the Builder
    private DriverDTO(Builder builder) {
        this.driverId = builder.driverId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.licenseNumber = builder.licenseNumber;
        this.mobileNo = builder.mobileNo;
        this.email = builder.email;
        this.isAvailable = builder.isAvailable;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.isDeleted = builder.isDeleted;
        this.addedUserId = builder.addedUserId;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public int getAddedUserId() {
        return addedUserId;
    }

    // Builder class
    public static class Builder {
        private int driverId;
        private String firstName;
        private String lastName;
        private String licenseNumber;
        private String mobileNo;
        private String email;
        private boolean isAvailable;
        private Date createdAt;
        private Date updatedAt;
        private boolean isDeleted;
        private int addedUserId;

        public Builder driverId(int driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder licenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
            return this;
        }

        public Builder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
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

        public Builder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder addedUserId(int addedUserId) {
            this.addedUserId = addedUserId;
            return this;
        }

        public DriverDTO build() {
            return new DriverDTO(this);
        }
    }
}