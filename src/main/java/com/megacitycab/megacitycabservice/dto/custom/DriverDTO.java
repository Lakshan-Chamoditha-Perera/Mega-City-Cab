package com.megacitycab.megacitycabservice.dto.custom;

import com.megacitycab.megacitycabservice.dto.DTO;

import java.sql.Date;

public class DriverDTO implements DTO {
    private int driverId;
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private String mobileNo;
    private String email;
    private boolean availability;
    private Date createdAt;
    private Date updatedAt;
    private boolean deleted;
    private int addedUserId;

    public static Builder builder() {
        return new Builder();
    }

    private DriverDTO(Builder builder) {
        this.driverId = builder.driverId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.licenseNumber = builder.licenseNumber;
        this.mobileNo = builder.mobileNo;
        this.email = builder.email;
        this.availability = builder.availability;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deleted = builder.deleted;
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

    public boolean getAvailability() {
        return availability;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public int getAddedUserId() {
        return addedUserId;
    }

    public static class Builder {
        private int driverId;
        private String firstName;
        private String lastName;
        private String licenseNumber;
        private String mobileNo;
        private String email;
        private boolean availability;
        private Date createdAt;
        private Date updatedAt;
        private boolean deleted;
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

        public DriverDTO build() {
            return new DriverDTO(this);
        }
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
                "driverId=" + driverId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", availability=" + availability +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deleted=" + deleted +
                ", addedUserId=" + addedUserId +
                '}';
    }
}