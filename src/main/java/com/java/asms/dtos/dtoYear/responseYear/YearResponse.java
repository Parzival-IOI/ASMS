package com.java.asms.dtos.dtoYear.responseYear;

import com.java.asms.dtos.dtoGeneration.generationResponse.GenerationResponse;
import com.java.asms.enums.YearStatus;
import com.java.asms.models.Year;
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

    public void responseYear(Year year) {
        this.id = year.getId();
        this.name = year.getName();
        this.studentNumber = year.getStudentNumber();
        this.expectedNumber = year.getExpectedNumber();
        this.yearStatus = year.getYearStatus();
        this.year = year.getYear();
        this.startDate = year.getStartDate();
        this.endDate = year.getEndDate();
        this.description = year.getDescription();

        if (year.getGeneration() != null) {
            GenerationResponse generationResponse = new GenerationResponse();
            generationResponse.response(year.getGeneration());
            this.generation = generationResponse;
        }
    }


}
