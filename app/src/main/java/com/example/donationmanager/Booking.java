package com.example.donationmanager;

import com.google.firebase.database.ServerValue;

public class Booking {

    private String bookingKey, charityName, description, donationType, furnitureType, dayName, timeSlot;

    public Booking(String charityName, String description, String donationType, String furnitureType, String dayName, String timeSlot) {
        this.charityName = charityName;
        this.description = description;
        this.donationType = donationType;
        this.furnitureType = furnitureType;
        this.dayName = dayName;
        this.timeSlot = timeSlot;
    }

    public Booking() {

    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
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
