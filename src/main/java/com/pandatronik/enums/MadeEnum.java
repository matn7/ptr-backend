package com.pandatronik.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MadeEnum {
    HUNDRED(1, 100), SEVENTY_FIVE(2, 75), FIFTY(3, 50), TWENTY_FIVE(4, 25), ZERO(5, 0);

    private final int id;

    private final int value;

    MadeEnum(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
