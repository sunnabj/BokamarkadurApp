package com.example.bokamarkadur;

import com.example.bokamarkadur.POJO.BookList;

import retrofit2.Call;
import retrofit2.http.GET;

interface APIInterface {
    @GET("/all-books")
    Call<BookList> getBooks();

    @GET("/newest-books")
    Call<BookList> getNewestBooks();
}
