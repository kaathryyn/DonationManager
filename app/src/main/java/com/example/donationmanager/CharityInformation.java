package com.example.donationmanager;

public class CharityInformation {

    public String charityName;
    public String address;
    public String city;
    public String postcode;
    public String state;
    public String phoneNumber;
    public String accountType;
    public String uId;
    public int openingHour;
    public int closingHour;
    public boolean mondayOpen;
    public boolean tuesdayOpen;
    public boolean wednesdayOpen;
    public boolean thursdayOpen;
    public boolean fridayOpen;
    public boolean saturdayOpen;
    public boolean sundayOpen;
    public boolean initialSetup = false;


    public CharityInformation(String charityName, String address, String city, String postcode, String state, String phoneNumber, String accountType, String uId, int openingHour, int closingHour, boolean mondayOpen, boolean tuesdayOpen, boolean wednesdayOpen, boolean thursdayOpen, boolean fridayOpen, boolean saturdayOpen, boolean sundayOpen, boolean initialSetup) {
        this.charityName = charityName;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.uId = uId;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.mondayOpen = mondayOpen;
        this.tuesdayOpen = tuesdayOpen;
        this.wednesdayOpen = wednesdayOpen;
        this.thursdayOpen = thursdayOpen;
        this.fridayOpen = fridayOpen;
        this.saturdayOpen = saturdayOpen;
        this.sundayOpen = sundayOpen;
        this.initialSetup = initialSetup;
    }

    public CharityInformation() {}


}
