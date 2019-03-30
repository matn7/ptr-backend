package com.pandatronik.enums;

public enum MadeEnum {
    HUNDRED(1, 100), SEVENTY_FIVE(2, 75), FIFTY(3, 50), TWENTY_FIVE(4, 25), ZERO(5, 0);

    private final int id;

    private final int made;

    MadeEnum(int id, int made) {
        this.id = id;
        this.made = made;
    }

    public int getId() {
        return id;
    }

    public int getMade() {
        return made;
    }
}
