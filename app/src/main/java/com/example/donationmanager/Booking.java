package com.example.donationmanager;

import com.google.firebase.database.ServerValue;

public class Booking {

    private String bookingKey;
    private String charityName;
    private String description;
    private Object timeStamp;

    public Booking(String charityName, String description) {
        this.charityName = charityName;
        this.description = description;
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
}
