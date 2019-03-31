package com.pandatronik.exceptions;

public class TokenExpiredResponse {

    private String tokenExpired;

    public TokenExpiredResponse() {
        this.tokenExpired = "Token has expired";
    }

    public String getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(String tokenExpired) {
        this.tokenExpired = tokenExpired;
    }
}
