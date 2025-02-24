package com.megacitycab.megacitycabservice.entity.custom;

import com.megacitycab.megacitycabservice.entity.Entity;

import java.sql.Date;
import java.time.LocalDateTime;

public class Customer implements Entity {
    private int customerId;
    private int addedUserId;
    private String firstName;
    private String lastName;
    private String address;
    private String nic;
    private Date dateOfBirth;
    private String mobileNo;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.addedUserId = builder.addedUserId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.address = builder.address;
        this.nic = builder.nic;
        this.dateOfBirth = builder.dateOfBirth;
        this.mobileNo = builder.mobileNo;
        this.email = builder.email;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.isDeleted = builder.isDeleted;
    }

    public static Builder builder() {
        return new Customer.Builder();
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAddedUserId() {
        return addedUserId;
    }

    public void setAddedUserId(int addedUserId) {
        this.addedUserId = addedUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public static class Builder {
        private int customerId;
        private int addedUserId;
        private String firstName;
        private String lastName;
        private String address;
        private String nic;
        private Date dateOfBirth;
        private String mobileNo;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;

        public Builder customerId(int customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder addedUserId(int addedUserId) {
            this.addedUserId = addedUserId;
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

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder nic(String nic) {
            this.nic = nic;
            return this;
        }

        public Builder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
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

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", addedUserId=" + addedUserId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", address='" + address + '\'' + ", nic='" + nic + '\'' + ", dateOfBirth=" + dateOfBirth + ", mobileNo='" + mobileNo + '\'' + ", email='" + email + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isDeleted=" + isDeleted + '}';
    }
}