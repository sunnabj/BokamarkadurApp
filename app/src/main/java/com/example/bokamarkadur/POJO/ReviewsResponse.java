package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {


    @SerializedName("errors")
    private String errors;
    @SerializedName("reviews")
    private List<Review> reviews;
    @SerializedName("msg")
    private String msg;
    @SerializedName("ok")
    private Boolean ok;

    public ReviewsResponse(String errors, List<Review> reviews, String msg, Boolean ok) {

        this.errors = errors;
        this.reviews = reviews;
        this.msg = msg;
        this.ok = ok;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Review> viewReviews() { return reviews; }

}
