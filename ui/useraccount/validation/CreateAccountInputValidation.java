package com.example.teamproject.ui.useraccount.validation;

import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Evan Hosking
 * Validates create account input fields
 */

public class CreateAccountInputValidation {
    /**
     * checks that all data entered is valid
     * @param balance
     * @param lastName
     * @return
     */
    public boolean checkInput(TextInputLayout balance, TextInputLayout lastName) {
        if (validateBalance(balance) && (validateLastName(lastName))) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * checks that balance is entered correctly
     * @param balance
     * @return
     */
    private boolean validateBalance(TextInputLayout balance) {
        String value = balance.getEditText().getText().toString();
        int num;
        if (value.isEmpty()) {
            balance.setError("This is a required field ");
            return false;
        }
        try {
            num = Integer.parseInt(value);
            if (num > 0 && num < 100) {
                balance.setError(null);
                return true;
            } else {
                balance.setError("Please enter a balance between 1 and 100");
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * checks that last name is entered correctly
     * @param lastName
     * @return
     */

    private boolean validateLastName(TextInputLayout lastName) {
        String value = lastName.getEditText().getText().toString();
        if (value.isEmpty()) {
            lastName.setError("This is a required field ");
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
}
