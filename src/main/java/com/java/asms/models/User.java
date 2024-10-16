package com.java.asms.models;

import com.java.asms.enums.UserRole;
import com.java.asms.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private UserStatus status;
    @Column(nullable = false)
    private UserRole role;
}
