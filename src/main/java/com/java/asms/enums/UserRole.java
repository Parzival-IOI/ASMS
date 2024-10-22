package com.java.asms.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    HR("HR"),
    ACCOUNT("ACCOUNT"),
    REGISTRAR("REGISTRAR"),
    TEACHER("TEACHER");

    private final String value;
    UserRole(String value) {
        this.value = value;
    }
}
