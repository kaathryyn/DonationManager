package com.example.donationmanager;

public class DonorInformation {

    public String firstName;
    public String lastName;
    public String address;
    public String accountType;
    public String uId;

    public DonorInformation(String firstName, String lastName, String address, String accountType, String uId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.accountType = accountType;
        this.uId = uId;
    }

    public DonorInformation() {}
}
