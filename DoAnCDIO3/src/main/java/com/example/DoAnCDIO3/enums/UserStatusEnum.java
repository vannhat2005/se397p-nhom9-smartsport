package com.example.DoAnCDIO3.enums;

public enum UserStatusEnum {
    ACTIVE(1),
    INACTIVE(0);

    private final int value;

    UserStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
