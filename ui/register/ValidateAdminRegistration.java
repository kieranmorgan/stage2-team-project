package com.example.teamproject.ui.register;

import android.content.Intent;
import android.util.Patterns;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author Kieran Morgan
 * Validates the registration input to ensure it meets the specified requirements.
 */

public class ValidateAdminRegistration {

    /**
     * checks whether an illegal value has been used- stops an sql injection
     * @param valueToBeChecked
     * @return
     */
    public boolean checkChars(String valueToBeChecked){
        String[] illegalChar = {"<", ">", "{", "}", "insert", "into", "where", "script", "delete", "input"};
        boolean allowed = true;
        for (int i = 0; i <10 ; i++){
            if (valueToBeChecked.contains(illegalChar[i])){
                allowed = false;
                return allowed;
            }
        }return allowed;
    }

    /**
     * cecks that the user's information is all present and correct
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param emailAddress
     * @param password
     * @return
     */
    public boolean checkValid(TextInputLayout firstName, TextInputLayout lastName, TextInputLayout phoneNumber,
                              TextInputLayout emailAddress, TextInputLayout password) {
        if (validateFirstName(firstName) && validateLastName(lastName) &&  validatePhoneNumber(phoneNumber) &&
                validateEmail(emailAddress) && validatePassword(password)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * checks that user's first name is allowed
     * @param firstName
     * @return
     */
    public boolean validateFirstName(TextInputLayout firstName) {
        String value = firstName.getEditText().getText().toString();
        if (value.isEmpty()) {
            firstName.setError("This is a required field ");
            return false;
        }

        if(checkChars(value)== false){
            firstName.setError("You used an illegal character or statement ");
            return false;
        }


        else if (value.length() > 0 & value.length() < 50) {
            firstName.setError(null);
            return true;
        }
        else {
            firstName.setError("First name must be between 0 and 50 characters");
            return false;
        }
    }

    /**
     * checks that user's last name is allowed
     * @param lastName
     * @return
     */
    public boolean validateLastName(TextInputLayout lastName) {
        String value = lastName.getEditText().getText().toString();
        if (value.isEmpty()) {
            lastName.setError("This is a required field ");
            return false;
        }

        if(checkChars(value)== false){
            lastName.setError("You used an illegal character or statement ");
            return false;
        }

        else if (value.length() > 0 & value.length() < 50) {
            lastName.setError(null);
            return true;
        }
        else {
            lastName.setError("Last name must be between 0 and 50 characters");
            return false;
        }
    }

    /**
     * checks that user's phone number is allowed
     * @param phoneNumber
     * @return
     */
    public boolean validatePhoneNumber(TextInputLayout phoneNumber) {
        String value = phoneNumber.getEditText().getText().toString();
        if (value.isEmpty()) {
            phoneNumber.setError("This is a required field ");
            return false;
        }
        else if (!value.startsWith("44")) {
            phoneNumber.setError("Please include UK country code (44)");
            return false;
        }
        else if (!(value.length() == 12)) {
            phoneNumber.setError("Phone number must be 12 digits");
            return false;
        }
        else {
            phoneNumber.setError(null);
            return true;
        }
    }

    /**
     * checks that user's email address is valid
     * @param emailAddress
     * @return
     */
    public boolean validateEmail(TextInputLayout emailAddress) {
        String value = emailAddress.getEditText().getText().toString();
        if (value.isEmpty()) {
            emailAddress.setError("This is a required field ");
            return false;
        }
        if(checkChars(value)== false){
            emailAddress.setError("You used an illegal character or statement ");
            return false;
        }

        else if (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            emailAddress.setError(null);
            return true;
        }
        else {
            emailAddress.setError("Please enter a valid UK university email address");
            return false;
        }
    }

    /**
     * checks that user's password conforms to all the rules
     * @param password
     * @return
     */
    public boolean validatePassword(TextInputLayout password) {
        String value = password.getEditText().getText().toString();
        if (value.isEmpty()) {
            password.setError("Please enter a 8 to 30 character password with at least one, " +
                    "number, lowercase, uppercase, special character");
            return false;
        }
        if(checkChars(value)== false){
            password.setError("You used an illegal character or statement ");
            return false;
        }
        else if (value.contains(" ")) {
            password.setError("No white space is allowed");
            return false;
        }
        else if (!Pattern.compile("(.*[a-z].*)").matcher(value).matches()) {
            password.setError("Please enter at least one lower case character");
            return false;
        }
        else if (!Pattern.compile("(.*[A-Z].*)").matcher(value).matches()) {
            password.setError("Please enter at least one upper case character");
            return false;
        }
        else if (!Pattern.compile("(.*[0-9].*)").matcher(value).matches()) {
            password.setError("Please enter at least one number");
            return false;
        }
        else if (!Pattern.compile("(.*[!?@#$%^&+=()*/£_.,{}|~].*)").matcher(value).matches()) {
            password.setError("Please enter at least one special character");
            return false;
        }
        else if (!(value.length() > 7 && value.length() < 31)) {
            password.setError("Please enter password between 8 and 30 characters");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }
}