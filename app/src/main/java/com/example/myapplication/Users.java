package com.example.myapplication;

public class Users {

    private String username;
    private String password;
    private Boolean isAdmin;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public Users(){

    }

    public Users(String username, String password, Boolean isAdmin){
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    //region Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
    //endregion



}
