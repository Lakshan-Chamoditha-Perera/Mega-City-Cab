package com.megacitycab.megacitycabservice.dto.custom;

import com.megacitycab.megacitycabservice.dto.DTO;

import java.sql.Date;
import java.time.LocalDateTime;

public class CustomerDTO implements DTO {
    private final int customerId;
    private final int addedUserId;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String nic;
    private final Date dateOfBirth;
    private final String mobileNo;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean isDeleted;

    private CustomerDTO(Builder builder) {
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
        return new Builder();
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAddedUserId() {
        return addedUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getNic() {
        return nic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return "CustomerDto{" + "customerId=" + customerId + ", addedUserId=" + addedUserId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", address='" + address + '\'' + ", nic='" + nic + '\'' + ", dateOfBirth=" + dateOfBirth + ", mobileNo='" + mobileNo + '\'' + ", email='" + email + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isDeleted=" + isDeleted + '}';
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

        public CustomerDTO build() {
            return new CustomerDTO(this);
        }
    }
}
