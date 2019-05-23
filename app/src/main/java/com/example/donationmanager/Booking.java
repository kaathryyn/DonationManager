package com.example.donationmanager;

import com.google.firebase.database.ServerValue;

public class Booking {

    private String bookingKey, charityName, donorID, description, donationType, furnitureType, dayName, timeSlot;
    long timeStamp;

    public Booking(String charityName, String donorID, String description, String donationType, String furnitureType, String timeSlot, long timeStamp) {
        this.charityName = charityName;
        this.donorID = donorID;
        this.description = description;
        this.donationType = donationType;
        this.furnitureType = furnitureType;
        this.timeSlot = timeSlot;
        this.timeStamp = timeStamp;
    }

    public Booking() {

    }




    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
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
