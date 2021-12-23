package com.example.teamproject.data.model;

/**
 * @author Evan Hosking
 * Data model storing information about user currently logged in
 */
public class LoggedInUser {

    private String customerID;
    private String firstName;
    private String emailAddress;

    public LoggedInUser(String userId, String firstName, String emailAddress) {
        this.customerID = userId;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
    }

    public String getUserId() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}