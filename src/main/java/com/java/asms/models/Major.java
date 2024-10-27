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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="majors")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Major extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String price;
    @Column(name = "study_year")
    private int studyYear;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private List<Generation> generations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private List<Subject> subject;
}
