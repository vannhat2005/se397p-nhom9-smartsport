package com.example.DoAnCDIO3.enums;

public enum FieldTypeStatusEnum {
    ACTIVE(1),
    INACTIVE(0);

    private final int value;

    FieldTypeStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
