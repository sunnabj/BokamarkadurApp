package com.example.bokamarkadur.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("info")
    @Expose
    private Object info;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("retypePassword")
    @Expose
    private Object retypePassword;
    @SerializedName("books")
    @Expose
    private List<Object> books = null;
    @SerializedName("receivedMessages")
    @Expose
    private List<Object> receivedMessages = null;
    @SerializedName("sentMessages")
    @Expose
    private List<Object> sentMessages = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(Object retypePassword) {
        this.retypePassword = retypePassword;
    }

    public List<Object> getBooks() {
        return books;
    }

    public void setBooks(List<Object> books) {
        this.books = books;
    }

    public List<Object> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Object> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Object> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Object> sentMessages) {
        this.sentMessages = sentMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", info=" + info +
                ", name=" + name +
                ", username='" + username + '\'' +
                ", email=" + email +
                ", retypePassword=" + retypePassword +
                ", books=" + books +
                ", receivedMessages=" + receivedMessages +
                ", sentMessages=" + sentMessages +
                '}';
    }
}