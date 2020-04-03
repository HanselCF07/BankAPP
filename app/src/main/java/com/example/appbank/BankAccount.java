package com.example.appbank;

import android.os.Parcel;
import android.os.Parcelable;

public class BankAccount implements Parcelable, Comparable<BankAccount> {
    private String clientName;
    private String accountNumber;
    private String accountType;
    private String balance;

    public BankAccount() {
    }

    public BankAccount(String clientName, String accountNumber, String accountType, String balance) {
        this.clientName = clientName;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clientName);
        dest.writeString(this.accountNumber);
        dest.writeString(this.accountType);
        dest.writeString(this.balance);
    }

    protected BankAccount(Parcel in) {
        this.clientName = in.readString();
        this.accountNumber = in.readString();
        this.accountType = in.readString();
        this.balance = in.readString();
    }

    public static final Creator<BankAccount> CREATOR = new Creator<BankAccount>() {
        @Override
        public BankAccount createFromParcel(Parcel source) {
            return new BankAccount(source);
        }

        @Override
        public BankAccount[] newArray(int size) {
            return new BankAccount[size];
        }
    };

    @Override
    public int compareTo(BankAccount o) {
        if(Float.parseFloat(o.getBalance()) > Float.parseFloat(balance)){
            return -1;
        }else if(Float.parseFloat(o.getBalance()) > Float.parseFloat(balance)){
            return 0;
        }else{
            return 1;
        }
    }
}
