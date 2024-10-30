package com.java.asms.models;

import jakarta.persistence.CascadeType;
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
@Entity(name="events")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String location;
    private String category;

    @ManyToOne
    @JoinColumn(name = "maker_id", referencedColumnName = "id")
    private User maker;

    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "id")
    private User checker;
}
