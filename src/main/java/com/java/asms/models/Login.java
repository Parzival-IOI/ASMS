package com.java.asms.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="logins")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Login extends BaseEntity {
    private long userId;
    @Column(columnDefinition="TEXT")
    private String refreshToken;
}
