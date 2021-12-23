package com.example.teamproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Evan Hosking
 * Data model storing information about a bank account
 * Implements parcelable to allow this object to be store and passed between pages,
 * using intent (addExtra)
 */

public class BankAccount implements Parcelable {
    private String AccountNumber;
    private String DigitalCardNumber;
    private double Balance;
    private String LastName;

    public BankAccount(String accountNumber, String DigitalCardNumber, double balance, String LastName) {
        this.AccountNumber = accountNumber;
        this.DigitalCardNumber = DigitalCardNumber;
        this.Balance = balance;
        this.LastName = LastName;
    }

    protected BankAccount(Parcel in) {
        AccountNumber = in.readString();
        DigitalCardNumber = in.readString();
        Balance = in.readDouble();
        LastName = in.readString();
    }

    public static final Creator<BankAccount> CREATOR = new Creator<BankAccount>() {
        @Override
        public BankAccount createFromParcel(Parcel in) {
            return new BankAccount(in);
        }

        @Override
        public BankAccount[] newArray(int size) {
            return new BankAccount[size];
        }
    };

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getDigitalCardNumber() {
        return DigitalCardNumber;
    }

    public void setDigitalCardNumber(String accountName) {
        DigitalCardNumber = accountName;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getLastName() {
        return LastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AccountNumber);
        dest.writeString(DigitalCardNumber);
        dest.writeDouble(Balance);
        dest.writeString(LastName);
    }
}
