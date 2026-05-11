package com.example.DoAnCDIO3.enums;

public enum FieldEnum {
    PENDING(2),

    ACTIVE(1),

    INACTIVE(0);

    private final int value;

    FieldEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
