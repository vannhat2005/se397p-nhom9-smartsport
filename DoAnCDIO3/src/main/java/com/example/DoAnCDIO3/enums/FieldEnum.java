package com.example.DoAnCDIO3.enums;

public enum FieldEnum {
    PENDING(0),

    ACTIVE(1),

    INACTIVE(2);

    private final int value;

    FieldEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
