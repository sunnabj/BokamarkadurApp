package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    // Vantar messages

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;
    @SerializedName("info")
    private String info;

    @SerializedName("email")
    private String email;
    @SerializedName("phonenumber")
    private int phonenumber;

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("retypePassword")
    private String retypePassword;

    @SerializedName("usersbooks")
    private List<Book> usersBooks;
    @SerializedName("token")
    private String token;

    // Fjarlægt héðan í bili: List<Message> messages
    public User (long id, String name, String info, String email, int phonenumber, String username, String password,
                String retypePassword, List<Book> usersBooks) {
        this.id = id;

        this.name = name;
        this.info = info;

        this.email = email;
        this.phonenumber = phonenumber;

        this.username = username;
        this.password = password;
        this.retypePassword = retypePassword;

        this.usersBooks = usersBooks;
    }

    public User(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }


    public String getEmail() {
        return email;
    }
    public int getPhonenumber() {
        return phonenumber;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRetypePassword() {
        return retypePassword;
    }

    public List<Book> getBooks() {
        return usersBooks;
    }


    public String getToken() {
        return token;
    }

    public void setId(long id) { this.id = id; }

    public void setName(String name) { this.name = name; }
    public void setInfo(String info) { this.info = info; }

    public void setEmail(String email) { this.email = email; }
    public void setPhonenumber(String email) { this.phonenumber = phonenumber; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRetypePassword(String retypePassword) { this.retypePassword = retypePassword; }


    public void setBooks(List<Book> books) { this.usersBooks = usersBooks; }


    public void setToken(String token) {
        this.token = token;
    }
}
