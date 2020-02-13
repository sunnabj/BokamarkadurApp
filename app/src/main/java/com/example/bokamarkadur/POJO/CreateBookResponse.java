package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

public class CreateBookResponse {

    @SerializedName("title")
    public String title;
    @SerializedName("author")
    public String author;
    @SerializedName("edition")
    public Integer edition;
}
