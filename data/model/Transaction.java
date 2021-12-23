package com.example.teamproject.data.model;

/**
 * @author Evan Hosking
 * Data model storing information about a transaction
 */

public class Transaction {

    private String digitalCardNumber;
    private double amount;
    private String Type;
    private String Time;
    private String date;
    private String transactor;


    public Transaction(String digitalCardNumber, double amount, String type, String time, String date, String transactor) {
        this.amount = amount;
        this.digitalCardNumber = digitalCardNumber;
        this.date = date;
        this.Time = time;
        this.transactor = transactor;
        this.Type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDigitalCardNumber() {
        return digitalCardNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return Time;
    }

    public String getTransactor() {
        return transactor;
    }

    public String getType() {
        return Type;
    }

}
