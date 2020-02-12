package com.example.bokamarkadur;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

class Books {


    public ArrayList<Books> getBooks() {
        return books;
    }

    @SerializedName("books")
    private ArrayList<Books> books;



    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String author;
    private Integer edition;
    private String condition;
    private Integer price;
    private String image;
    private String status;
    private Date date;
}
