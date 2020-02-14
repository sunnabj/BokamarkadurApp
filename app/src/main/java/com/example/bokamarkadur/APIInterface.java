package com.example.bokamarkadur;

import com.example.bokamarkadur.POJO.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;

interface APIInterface {
    @GET("/books")
    Call<BookResponse> getBookList();
}
