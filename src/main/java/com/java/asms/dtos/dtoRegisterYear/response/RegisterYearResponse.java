package com.java.asms.dtos.dtoRegisterYear.response;

import com.java.asms.dtos.dtoStudent.responseStudent.StudentResponse;
import com.java.asms.dtos.dtoYear.responseYear.YearResponse;
import lombok.*;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterYearResponse {
    private long id;
    private Boolean isPassed;
    private long score;
    private Date dateExam;
    private StudentResponse student;
    private YearResponse year;
}
