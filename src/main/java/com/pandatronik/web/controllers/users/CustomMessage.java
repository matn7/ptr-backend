package com.pandatronik.web.controllers.users;

public class CustomMessage {

    String message;

    public CustomMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
