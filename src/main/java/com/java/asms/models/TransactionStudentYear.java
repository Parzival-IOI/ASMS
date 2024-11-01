package com.java.asms.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="transaction_student_year")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class TransactionStudentYear extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "transaction_id")
    private String transactionID;
    private String channel;
    private long paid;

    @Column(name = "student_id")
    private long studentId;

    @ManyToOne
    @JoinColumn(name = "maker_id", referencedColumnName = "id")
    private User maker;

    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "id")
    private User checker;

    @ManyToOne
    @JoinColumn(name = "student_year_id", referencedColumnName = "id")
    private StudentYear studentYear;
}
