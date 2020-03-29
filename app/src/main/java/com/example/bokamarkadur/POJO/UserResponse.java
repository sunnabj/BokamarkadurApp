package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("errors")
    private String errors;
    @SerializedName("user")
    private User user;
    @SerializedName("ok")
    private Boolean ok;
    @SerializedName("msg")
    private String msg;

    public UserResponse(String errors, User user, Boolean ok, String msg) {

        this.errors = errors;
        this.user = user;
        this.ok = ok;
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
