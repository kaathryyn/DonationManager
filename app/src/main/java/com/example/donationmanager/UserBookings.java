package com.example.donationmanager;

public class UserBookings {

    public String charity;
    public String donationMode;
    public Boolean active;
    public String feedbackComment;
    public int feedbackRating;

    public UserBookings(String charity, String donationMode, Boolean active, String feedbackComment, int feedbackRating) {
        this.charity = charity;
        this.donationMode = donationMode;
        this.active = active;
        this.feedbackComment = feedbackComment;
        this.feedbackRating = feedbackRating;
    }
}
