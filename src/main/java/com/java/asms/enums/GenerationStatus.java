package com.java.asms.enums;

import lombok.Getter;

@Getter
public enum GenerationStatus {
    LOCKED("LOCKED"),
    DISABLED("DISABLED"),
    ENABLE("ENABLE");

    private final String value;
    GenerationStatus(String value) {
        this.value = value;
    }
}
