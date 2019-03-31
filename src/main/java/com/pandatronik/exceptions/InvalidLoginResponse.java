package com.pandatronik.exceptions;

public class InvalidLoginResponse {

    private String username;
    private String password;
    private String tokenExpired;

    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }

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

    public String getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(String tokenExpired) {
        this.tokenExpired = tokenExpired;
    }
}