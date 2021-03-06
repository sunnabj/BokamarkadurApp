package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Book {

    // Vantar að bæta við messages.

    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;
    @SerializedName("edition")
    private Integer edition;
    @SerializedName("condition")
    private String condition;
    @SerializedName("price")
    private Integer price;
    @SerializedName("image")
    private String image;
    @SerializedName("status")
    private String status;
    @SerializedName("date")
    private Date date;
    @SerializedName("user")
    private User user;
//    @SerializedName("messages")
//    private Message messages;
    @SerializedName("subject")
    private String subject;

    // Fjarlægt héðan í bili: List<Message> messages
    public Book(long id, String title, String author, Integer edition, String condition,
                Integer price, String image, String status, Date date, User user, String subject) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.condition = condition;
        this.price = price;
        this.image = image;
        this.status = status;
        this.date = date;
        this.user = user;
//        this.messages = messages;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getEdition() {
        return edition;
    }

    public String getCondition() {
        return condition;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() { return image; }

    public String getStatus() { return status; }

    public Date getDate() { return date; }

    public User getUser() { return user; }

    public String getSubject() { return subject; }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImage(String image) { this.image = image; }

    public void setStatus(String status) { this.status = status; }

    public void setDate(Date date) { this.date = date; }

    public void setUser(User user) { this.user = user; }

    public void setSubject(String subject) { this.subject = subject; }
}

