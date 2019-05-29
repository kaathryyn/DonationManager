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

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(int openingHour) {
        this.openingHour = openingHour;
    }

    public int getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(int closingHour) {
        this.closingHour = closingHour;
    }

    public boolean isMondayOpen() {
        return mondayOpen;
    }

    public void setMondayOpen(boolean mondayOpen) {
        this.mondayOpen = mondayOpen;
    }

    public boolean isTuesdayOpen() {
        return tuesdayOpen;
    }

    public void setTuesdayOpen(boolean tuesdayOpen) {
        this.tuesdayOpen = tuesdayOpen;
    }

    public boolean isWednesdayOpen() {
        return wednesdayOpen;
    }

    public void setWednesdayOpen(boolean wednesdayOpen) {
        this.wednesdayOpen = wednesdayOpen;
    }

    public boolean isThursdayOpen() {
        return thursdayOpen;
    }

    public void setThursdayOpen(boolean thursdayOpen) {
        this.thursdayOpen = thursdayOpen;
    }

    public boolean isFridayOpen() {
        return fridayOpen;
    }

    public void setFridayOpen(boolean fridayOpen) {
        this.fridayOpen = fridayOpen;
    }

    public boolean isSaturdayOpen() {
        return saturdayOpen;
    }

    public void setSaturdayOpen(boolean saturdayOpen) {
        this.saturdayOpen = saturdayOpen;
    }

    public boolean isSundayOpen() {
        return sundayOpen;
    }

    public void setSundayOpen(boolean sundayOpen) {
        this.sundayOpen = sundayOpen;
    }

    public boolean isInitialSetup() {
        return initialSetup;
    }

    public void setInitialSetup(boolean initialSetup) {
        this.initialSetup = initialSetup;
    }
}
