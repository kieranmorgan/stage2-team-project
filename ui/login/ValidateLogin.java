package com.example.teamproject.ui.login;

import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Evan Hosking
 * Validates the login input
 */

public class ValidateLogin {

    public boolean checkValid(TextInputLayout username, TextInputLayout password) {
        if (validateUsername(username) && validatePassword(password)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateUsername(TextInputLayout firstName) {
        String value = firstName.getEditText().getText().toString();
        if (value.isEmpty()) {
            firstName.setError("This is a required field ");
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

    public boolean validatePassword(TextInputLayout password) {
        String value = password.getEditText().getText().toString();
        if (value.isEmpty()) {
            password.setError("This is a required field");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }
}
