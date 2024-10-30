package com.java.asms.models;

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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.CustomLog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="student_year")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class StudentYear extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "is_paid")
    private Boolean isPaid;
    @Column(name = "type_transaction")
    private String typeTransaction;
    private double GPA;
    @Column(name = "subject_score")
    private String subjectScore;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "year_id", referencedColumnName = "id")
    private Year year;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentYear")
    private List<TransactionStudentYear> transactionStudentYears;
}
