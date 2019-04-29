package com.example.donationmanager;

public class UserBookings {

    public String charity;
    public String donationMode;
    public Boolean active;
    public String feedbackComment;
    public int feedbackRating;
    public int timeStamp;

    public UserBookings(String charity, String donationMode, Boolean active, String feedbackComment, int feedbackRating, int timeStamp) {
        this.charity = charity;
        this.donationMode = donationMode;
        this.active = active;
        this.feedbackComment = feedbackComment;
        this.feedbackRating = feedbackRating;
        this.timeStamp = timeStamp;
    }
}
