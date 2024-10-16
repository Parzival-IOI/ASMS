package com.java.asms.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name="blocked")
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Blocked extends BaseEntity {
    private int userId;
    @Column(nullable = false)
    private int attempt;
    @Column(nullable = false)
    private boolean isBlocked;
    private Date blockDate;
}
