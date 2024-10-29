package com.java.asms.dtos.dtoStudent.responseStudent;

import com.java.asms.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private long id;
    private String firstName;
    private String lastName;
    private Date dob;
    private StudentStatus studentStatus;
    private String address;
    private String phone;
    private String email;
    private String guardianPhone;
    private String parentPhone;

}
