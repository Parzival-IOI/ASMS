package com.java.asms.models;


import com.java.asms.enums.YearStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="years")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Year extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Column(name = "student_number")
    private int studentNumber;
    @Column(name = "expected_number")
    private int expectedNumber;

    @Column(name = "year_status")
    YearStatus yearStatus;
    private int year;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "generation_id", referencedColumnName = "id")
    private Generation generation;

    @OneToMany( mappedBy = "year")
    private List<SubjectYear> subjectYear;

    @OneToMany( mappedBy = "year")
    private List<RegisterYear> registerYears;

    @OneToMany( mappedBy = "year")
    private List<StudentYear> studentYears;
}
