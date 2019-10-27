package com.pandatronik.enums;

public enum TokenNotFoundEnum {
    TOKEN_NOT_FOUND("TokenNotFoundHeader", "Token not found");

    private final String id;

    private final String message;

    TokenNotFoundEnum(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
