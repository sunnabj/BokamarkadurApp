package com.example.bokamarkadur;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.POJO.SubjectsResponse;
import com.example.bokamarkadur.POJO.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface APIInterface {
    @GET("/all-books")
    Call<BookList> getBooks();

    @GET("/newest-books")
    Call<BookList> getNewestBooks();

    @GET("/available-subjects")
    Call<SubjectsResponse> getAvailableSubjects();

    @GET("/viewsubjectbooks/{subjects}")
    Call<BookList> getBooksBySubject(@Path("subjects") String subjects);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/addbookforsale")
    Call<Book> addBookForSale(@Body JsonObject body, @Header("Authorization") String authHeader);

    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/addrequestbook")
    Call<Book> addBookRequested(@Body JsonObject body); //, @Header("Authorization") String authHeader);

    @POST("/authenticate")
    Call<User> login(@Body JsonObject body);

    @POST("/register")
    Call<User> register(@Body JsonObject body);

}
