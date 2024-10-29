package com.java.asms.dtos.dtoRegisterYear.request;

import com.java.asms.models.Student;
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
public class RegisterYearRequest {
    private Date dateExam;
    private long score;
    private Boolean isPassed;
    private long studentId;
    private long yearId;
}
