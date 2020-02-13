package com.example.bokamarkadur;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.MultipleResource;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface APIInterface {

    @GET("/books")
    Call<MultipleResource> doGetBookList();

    @POST("/addbookforsale")
    Call<Book> createUser(@Body Book book);

}
