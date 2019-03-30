package com.pandatronik.payload;

import javax.validation.constraints.NotBlank;

public class EmailRequest {

    @NotBlank(message = "Email cannot be blank")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
