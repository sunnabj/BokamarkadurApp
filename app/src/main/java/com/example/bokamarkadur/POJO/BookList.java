package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {

    @SerializedName("books")
    public List<Book> book = null;
}