package com.java.asms.enums;

public enum LoginStatus {
    LOCKED("LOCKED"),
    DISABLED("DISABLED"),
    ENABLE("ENABLE");

    private final String value;
    LoginStatus(String value) {
        this.value = value;
    }
}
