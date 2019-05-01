package com.example.donationmanager;

public class UserInformation {

    public String firstName;
    public String lastName;
    public String address;
    public String accountType;

    public UserInformation(String firstName, String lastName, String address, String accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.accountType = accountType;
    }

    public UserInformation() {}
}
