package com.java.asms.models;

import com.java.asms.enums.LoginStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique=true)
    private String username;
    @Column(nullable=false)
    private String password;
    @Column(name = "refresh_token", columnDefinition = "text")
    private String refreshToken;
    @Column(nullable = false)
    private LoginStatus status;
    @Column(nullable = false, name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "blocked_date")
    private Date blockedDate;
    @Column(nullable = false)
    private int attempt;
    @Column(nullable = false, name = "is_student")
    private Boolean isStudent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
}
