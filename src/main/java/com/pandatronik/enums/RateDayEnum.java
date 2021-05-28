package com.pandatronik.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RateDayEnum {
    VERY_GOOD(1, 100), GOOD(2, 75), OK(3, 50), BAD(4, 25), VERY_BAD(5, 0);

    private final int id;

    private final int rateDay;

    RateDayEnum(int id, int rateDay) {
        this.id = id;
        this.rateDay = rateDay;
    }

    public int getId() {
        return id;
    }

    @JsonValue
    public int getRateDay() {
        return rateDay;
    }
}
