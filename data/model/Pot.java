package com.example.teamproject.data.model;

/**
 * @author Evan Hosking
 * Data model storing information about a pot
 */

public class Pot {
    String potName;
    String digitalCardNumber;
    String potDescription;
    double amount;

    public Pot(String potName, String digitalCardNumber, String potDescription, double amount) {
        this.potName = potName;
        this.digitalCardNumber = digitalCardNumber;
        this.potDescription = potDescription;
        this.amount = amount;
    }

    public String getPotName() {
        return potName;
    }

    public String getDigitalCardNumber() {
        return digitalCardNumber;
    }

    public String getPotDescription() {
        return potDescription;
    }

    public double getAmount() {
        return amount;
    }
}
