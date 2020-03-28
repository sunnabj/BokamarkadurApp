package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList {

    @SerializedName("reviews")
    private List<Review> reviews;

    public List<Review> viewReviews() { return reviews; }

    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

}
