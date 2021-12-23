package com.example.teamproject.ui.useraccount.validation;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

/**
 * @author Evan Hosking
 * Validates all transaction inputs to ensure they meet project requirements.
 * Ensures all data is in a valid format before an transaction is attempted.
 */

public class TransactionInputValidation {

    // does not check if sufficient funds in account

    /**
     * checks that all data ihs been entered correctly
     * @param digitalCardNumber
     * @param amount
     * @return
     */
    public boolean checkPayData(TextInputLayout digitalCardNumber, TextInputLayout amount) {
        if (validateDigitalCardNumber(digitalCardNumber) && (validateAmount(amount))) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkPurchaseData(TextInputLayout shopName, TextInputLayout amount) {
        if (validateShopName(shopName) && (validateAmount(amount))) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkWithdrawData(TextInputLayout amount) {
        if (validateAmount(amount)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateDigitalCardNumber(TextInputLayout digitalCardNumber) {
        String value = digitalCardNumber.getEditText().getText().toString();
        if (value.isEmpty()) {
            digitalCardNumber.setError("This is a required field ");
            return false;
        }
        else if (!Pattern.compile("^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$").matcher(value).matches()) {
            digitalCardNumber.setError("Please enter in the format xxxx-xxxx-xxxx-xxxx");
            return false;
        }
        else if (!(value.length() > 15 & value.length() < 25)) {
            digitalCardNumber.setError("Please enter a valid account number ");
            return false;
        }
        else {
            digitalCardNumber.setError(null);
            return true;
        }
    }

    public boolean validateAmount(TextInputLayout amount) {
        String value = amount.getEditText().getText().toString();
        int num;
        if (value.isEmpty()) {
            amount.setError("This is a required field ");
            return false;
        }
        try {
            num = Integer.parseInt(value);
            if (num > 0 && num < 500) {
                amount.setError(null);
                return true;
            } else {
                amount.setError("Invalid amount, must be greater than 0 or less than 500");
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateShopName(TextInputLayout reference) {
        String value = reference.getEditText().getText().toString();
        if (value.isEmpty()) {
            reference.setError("Please enter a shop name");
            return false;
        } else {
            reference.setError(null);
            return true;
        }
    }

}
