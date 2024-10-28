package com.java.asms.dtos.dtoYear.requestYear;

import com.java.asms.enums.YearStatus;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class YearRequest {
    private String name;
    private int studentNumber;
    private int expectedNumber;
    YearStatus yearStatus;
    private int year;
    private String description;
    private long generationId;
    private Date startDate;
    private Date endDate;
}
