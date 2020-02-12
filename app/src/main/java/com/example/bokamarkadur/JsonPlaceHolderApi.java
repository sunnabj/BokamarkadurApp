package com.example.bokamarkadur;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
/*
    @GET("posts")
    Call<List<Post>> getPosts();
*/
    @GET("books")
    Call<List<Books>> getBooks();
    Books save(Books book);
    void delete(Books book);
    List<Books> findAll();
    Optional<Books> findById(long id);
    List<Books> findByAuthorOrTitle(String statusSearch, String authorSearch, String titleSearch);
    //List<Books> findByUser(User user);
    //List<Books> findBySubjects(Subjects subject);
    List<Books> findNewestBooks();
}