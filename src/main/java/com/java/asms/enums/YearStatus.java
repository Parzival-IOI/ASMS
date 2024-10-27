package com.java.asms.enums;

import lombok.Getter;

@Getter
public enum YearStatus {
    LOCKED("LOCKED"),
    DISABLED("DISABLED"),
    ENABLE("ENABLE");

    private final String value;
    YearStatus(String value) {
        this.value = value;
    }
}
