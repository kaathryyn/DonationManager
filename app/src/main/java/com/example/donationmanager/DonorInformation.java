package com.example.donationmanager;

public class DonorInformation {

    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String postcode;
    public String state;
    public String phoneNumber;
    public String accountType;
    public String uId;
    boolean initialSetup = false;


    public DonorInformation(String firstName, String lastName, String address, String city, String postcode, String state, String phoneNumber, String accountType, String uId, Boolean initialSetup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.uId = uId;
        this.initialSetup = initialSetup;
    }

    public DonorInformation() {}
}
