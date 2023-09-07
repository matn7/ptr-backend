package com.pandatronik.payload;

import jakarta.validation.constraints.NotBlank;

public class PasswordRequest {

    @NotBlank(message = "Password cannot be blank")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
