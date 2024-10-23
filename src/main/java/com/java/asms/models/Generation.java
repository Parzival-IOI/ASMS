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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="generations")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Generation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "generation_status")
    private GenerationStatus generationStatus;

    @Column(name = "expected_number")
    private int expectedNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generation")
    private List<Year> years;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Major major;
}
