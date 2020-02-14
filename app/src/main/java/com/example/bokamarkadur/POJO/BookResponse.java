package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;


public class BookResponse {

    @SerializedName("_embedded")
    private BookList bookList;
    @SerializedName("_links")
    private Object links;
    @SerializedName("page")
    private Page page;

    public BookList getBookList() {
        return bookList;
    }

    public Object getLinks() {
        return links;
    }

    public Page getPage() {
        return page;
    }

    public void setBookList(BookList bookList) {
        this.bookList = bookList;
    }

    public void setLinks(Object links) {
        this.links = links;
    }

    public void setPage(Page page) {
        this.page = page;
    }

//    public List<Book> getResults() {
//        return results;
//    }
//
//    public void setResults(List<Book> results) {
//        this.results = results;
//    }

}
