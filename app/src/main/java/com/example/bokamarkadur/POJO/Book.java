package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

public class Book {


    @SerializedName("title")
    public String title;
    @SerializedName("author")
    public String author;
    @SerializedName("edition")
    public Integer edition;
//    @SerializedName("condition")
//    public String condition;
//    @SerializedName("price")
//    public Integer price;
    @SerializedName("image")
    public String image;
//    @SerializedName("status")
//    public String status;

    public Book(String title, String author, Integer edition) {
        this.title = title;
        this.author = author;
        this.edition = edition;
//        this.condition = condition;
//        this.price = price;
        this.image = image;
//        this.status = status;
    }
}

