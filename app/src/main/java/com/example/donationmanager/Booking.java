package com.example.donationmanager;

import com.google.firebase.database.ServerValue;

public class Booking {

    private String bookingKey, charityName, description, donationType, furnitureType;
    private Object timeStamp;

    public Booking(String charityName, String description, String donationType, String furnitureType) {
        this.charityName = charityName;
        this.description = description;
        this.donationType = donationType;
        this.furnitureType = furnitureType;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public Booking() {

    }

    public String getBookingKey() {
        return bookingKey;
    }

    public void setBookingKey(String bookingKey) {
        this.bookingKey = bookingKey;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getFurnitureType() {
        return furnitureType;
    }

    public void setFurnitureType(String furnitureType) {
        this.furnitureType = furnitureType;
    }
}
