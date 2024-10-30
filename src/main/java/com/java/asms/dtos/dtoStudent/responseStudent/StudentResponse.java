package com.java.asms.dtos.dtoStudent.responseStudent;

import com.java.asms.enums.StudentStatus;
import com.java.asms.models.Student;
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
    public void responseStudent(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.dob = student.getDob();
        this.studentStatus = student.getStudentStatus();
        this.address = student.getAddress();
        this.phone = student.getPhone();
        this.email = student.getEmail();
        this.guardianPhone = student.getGuardianPhone();
        this.parentPhone = student.getParentPhone();
    }


}
