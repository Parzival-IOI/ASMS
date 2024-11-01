package com.java.asms.models;

import com.java.asms.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false)
    private Date dob;
    @Column(nullable = false)
    private UserRole role;
    @Column(nullable = false)
    private String phone;
    private String email;
    @Column(name = "national_id", nullable = false)
    private String nationalId;
    @Column(nullable = false)
    private String address;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Login login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maker", orphanRemoval = true)
    private List<Event> eventsMaker;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checker", orphanRemoval = true)
    private List<Event> eventsChecker;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maker", orphanRemoval = true)
    private List<TransactionStudentYear> transactionsMaker;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checker", orphanRemoval = true)
    private List<TransactionStudentYear> transactionsChecker;

}
