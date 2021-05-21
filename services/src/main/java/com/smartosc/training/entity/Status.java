package com.smartosc.training.entity;

public enum Status {

    ACTIVE(1), INACTIVE(0);

    private final int value;

    Status(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
