package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {

    @SerializedName("books")
    public List<Book> book = null;

    public class Book {

        @SerializedName("title")
        public String title;
        @SerializedName("author")
        public String author;
        @SerializedName("edition")
        public Integer edition;
    }
}