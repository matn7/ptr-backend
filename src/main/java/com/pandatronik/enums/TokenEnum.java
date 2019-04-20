package com.pandatronik.enums;

public enum TokenEnum {
    TOKEN_EXPIRED("TokenExpiredHeader", "Token has expired");

    private final String id;

    private final String message;

    TokenEnum(String id, String message) {
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
