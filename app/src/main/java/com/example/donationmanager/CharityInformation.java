package com.example.donationmanager;

public class CharityInformation {

    public String charityName;
    public String address;
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


    public CharityInformation(String charityName, String address, String accountType, String uId, int openingHour, int closingHour, boolean mondayOpen, boolean tuesdayOpen, boolean wednesdayOpen, boolean thursdayOpen, boolean fridayOpen, boolean saturdayOpen, boolean sundayOpen) {
        this.charityName = charityName;
        this.address = address;
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
    }

    public CharityInformation() {}


}
