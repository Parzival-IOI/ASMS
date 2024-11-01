package com.java.asms.models;

import com.java.asms.enums.StudentStatus;
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
@Entity(name="students")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Date dob;
    @Column(name = "student_status")
    private StudentStatus studentStatus;
    private String address;
    private String phone;
    private String email;
    @Column(name = "guardian_phone")
    private String guardianPhone;
    @Column(name = "parent_phone")
    private String parentPhone;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true)
    private Login login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<RegisterYear> registerYears;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<StudentYear> studentYears;

}
