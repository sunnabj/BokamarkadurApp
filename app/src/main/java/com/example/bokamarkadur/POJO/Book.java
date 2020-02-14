package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Book {

    // Vantar að bæta við: subjects, user & messages.
    @SerializedName("id")
    public long id;
    @SerializedName("title")
    public String title;
    @SerializedName("author")
    public String author;
    @SerializedName("edition")
    public Integer edition;
    @SerializedName("condition")
    public String condition;
    @SerializedName("price")
    public Integer price;
    @SerializedName("image")
    public String image;
    @SerializedName("status")
    public String status;
    @SerializedName("date")
    public Date date;

    // Fjarlægt héðan í bili: Subjects subjects, User user, List<Message> messages
    public Book(long id, String title, String author, Integer edition, String condition,
                Integer price, String image, String status, Date date) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.condition = condition;
//        this.subjects = subjects;
        this.price = price;
        this.image = image;
        this.status = status;
        this.date = date;
//        this.user = user;
//        this.messages = messages;
    }

//    public List<Message> getMessages() {
//        return messages;
//    }

//    public void setMessages(List<Message> messages) {
//        this.messages = messages;
//    }

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

//    public Subjects getSubjects() {return subjects; }

    public Integer getPrice() {
        return price;
    }

    public String getImage() { return image; }

    public String getStatus() { return status; }

    public Date getDate() { return date; }

//    public User getUser() { return user; }

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

//    public void setSubjects(Subjects subjects) { this.subjects = subjects; }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImage(String image) { this.image = image; }

    public void setStatus(String status) { this.status = status; }

    public void setDate(Date date) { this.date = date; }

//    public void setUser(User user) { this.user = user; }
}

