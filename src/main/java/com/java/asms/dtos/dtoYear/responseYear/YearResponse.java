package com.java.asms.dtos.dtoYear.responseYear;

import com.java.asms.dtos.dtoGeneration.generationResponse.GenerationResponse;
import com.java.asms.enums.YearStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YearResponse {
    private long id;
    private String name;
    private int studentNumber;
    private int expectedNumber;
    YearStatus yearStatus;
    private int year;
    private Date startDate;
    private Date endDate;
    private String description;
    private GenerationResponse generation;

}
