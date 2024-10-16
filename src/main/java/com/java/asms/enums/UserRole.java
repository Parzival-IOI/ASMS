package com.java.asms.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    STAFF("STAFF"),
    TEACHER("TEACHER"),
    STUDENT("STUDENT"),
    PARENT("PARENT");

    private final String value;
    UserRole(String value) {
        this.value = value;
    }
}
