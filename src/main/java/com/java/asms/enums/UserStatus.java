package com.java.asms.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    LOCKED("LOCKED"),
    ENABLE("ENABLE");

    private final String value;
    UserStatus(String value) {
        this.value = value;
    }
}
