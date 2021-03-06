package com.example.bokamarkadur.Activities;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.POJO.Review;
import com.example.bokamarkadur.POJO.ReviewsResponse;
import com.example.bokamarkadur.POJO.SubjectsResponse;
import com.example.bokamarkadur.POJO.User;
import com.example.bokamarkadur.POJO.UserResponse;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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


//    @GET("api/Profiles/GetProfile?id={id}")
//    Call<UserProfile> getUser(@Path("id") String id, @Header("Authorization") String authHeader);

    @Multipart
    @POST("/addbookforsale")
    Call<Book> addBookForSale(@Header("Accept") String accept,
                              @Header("Authorization") String authorization,
                              @Part MultipartBody.Part file,
                              @Part("title") String title,
                              @Part("author") String author,
                              @Part("edition") int edition,
                              @Part("condition") String condition,
                              @Part("price") int price,
                              @Part("subject") String subject);

    @Multipart
    @POST("/addbookforsalenoimage")
    Call<Book> addBookForSaleNoImg(@Header("Accept") String accept,
                                   @Header("Authorization") String authorization,
                                   @Part("title") String title,
                                   @Part("author") String author,
                                   @Part("edition") int edition,
                                   @Part("condition") String condition,
                                   @Part("price") int price,
                                   @Part("subject") String subject);

    @Multipart
    @POST("/addrequestbook")
    Call<Book> addBookRequested(@Header("Accept") String accept,
                                @Header("Authorization") String authorization,
                                @Part MultipartBody.Part file,
                                @Part("title") String title,
                                @Part("author") String author,
                                @Part("edition") int edition,
                                @Part("subject") String subject);

    @Multipart
    @POST("/addrequestbooknoimage")
    Call<Book> addBookRequestedNoImg(@Header("Accept") String accept,
                                @Header("Authorization") String authorization,
                                @Part("title") String title,
                                @Part("author") String author,
                                @Part("edition") int edition,
                                @Part("subject") String subject);

    @POST("/authenticate")
    Call<User> login(@Body JsonObject body);


    @GET("/logout")
    Call<User> logout();


    @POST("/register")
    Call<User> register(@Body JsonObject body);

    @GET("/viewuser/{username}")
    Call<UserResponse> viewUser(@Path("username") String username);

    @GET("/viewuser/{username}")
    Call<User> getUserProfile(@Path("username") String username);


    @POST("/writeReview/{username}")
    Call<Review> writeReview(@Header("Authorization") String authorization,
                             @Header("Accept") String accept,
                             @Path("username") String username, @Body JsonObject body);

    @GET("/viewReviews/{username}")
    Call<ReviewsResponse> viewReviews(@Path("username") String username);


    @GET("/viewWrittenReviews/{username}")
    Call<ReviewsResponse> viewWrittenReviews(@Path("username") String username);


    // Gets list of books for user from "BookController.java" in REST Backend
    @GET("/myBooks")
    Call<BookList> myBooks(@Header("Authorization") String authorization);


    @POST("/updateUserProfile")
    Call<User> updateUserProfile(@Body JsonObject body,
                                 @Header("Accept") String accept,
                                 @Header("Authorization") String authorization);




    @GET("/loggedIn")
    Call<User> getLoggedInUser(@Header("Authorization") String authorization);


    @DELETE("/delete/{id}")
    Call<Book> deleteBook(@Path("id") long id, @Header("Authorization") String authorization);


}
