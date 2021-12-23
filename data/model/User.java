package com.example.teamproject.data.model;

/**
 * @author Matthew Holdsworth
 * Data model storing information about user
 */

public class User {
    String customerID;
    String lastName;
    String phoneNumber;
    String email;

    public User(String customerID, String lastName, String phoneNumber, String email) {
        this.customerID = customerID;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
