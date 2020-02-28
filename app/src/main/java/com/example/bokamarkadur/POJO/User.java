package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    // Vantar messages

    @SerializedName("id")
    private long id;
    @SerializedName("info")
    private String info;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("retypePassword")
    private String retypePassword;
    @SerializedName("books")
    private List<Book> books;

    // Fjarlægt héðan í bili: List<Message> messages
    public User(long id, String info, String name, String username, String email, String password,
                String retypePassword, List<Book> books) {

        this.id = id;
        this.info = info;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.retypePassword = retypePassword;
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
