package com.java.asms.enums;

import lombok.Getter;

@Getter
public enum StudentStatus {
    REGISTERED("REGISTERED"),
    DISABLED("DISABLED"),
    ENABLE("ENABLE");

    private final String value;
    StudentStatus(String value) {
        this.value = value;
    }
}
