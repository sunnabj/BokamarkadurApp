package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {

    @SerializedName("books")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getNewestBooks() { return books; }

    public List<Book> getBooksBySubject() { return books; }
    // getUsersBooks
    public List<Book> getUsersBooks() { return books; }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}