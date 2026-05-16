package com.example.DoAnCDIO3.enums;

public enum BookingEnum {
    PENDING(0),

    ACTIVE(1),

    INACTIVE(2);

    private final int value;

    BookingEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
