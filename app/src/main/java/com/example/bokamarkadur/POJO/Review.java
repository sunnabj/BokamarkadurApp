package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    private long id;
    @SerializedName("reviewer")
    private User reviewer;
    @SerializedName("user")
    private User user;
    @SerializedName("reviewBody")
    private String reviewBody;

    public Review(long id, User reviewer, User user, String reviewBody) {
        this.id = id;
        this.reviewer = reviewer;
        this.user = user;
        this.reviewBody = reviewBody;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }
}
