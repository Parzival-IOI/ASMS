package com.java.asms.models;

import com.java.asms.enums.GenerationStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
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
    private int generation;
    private String name;
    @Column(name = "student_number")
    private int studentNumber;
    @Column(name = "expected_number")
    private int expectedNumber;
    @Column(name = "generation_status")
    private GenerationStatus generationStatus;
    private int year;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Major major;
}
