package com.example.donationmanager;

public class UserInformation {

    public String name;
    public String address;
    public String accountType;
    public int phoneNumber;

    public UserInformation(String name, String address, String accountType, int phoneNumber) {
        this.name = name;
        this.address = address;
        this.accountType = accountType;
        this.phoneNumber = phoneNumber;
    }
}
