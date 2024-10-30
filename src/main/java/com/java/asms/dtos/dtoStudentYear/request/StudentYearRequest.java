package com.java.asms.dtos.dtoStudentYear.request;

import com.java.asms.models.Classroom;
import com.java.asms.models.Student;
import com.java.asms.models.StudentYear;
import com.java.asms.models.Year;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentYearRequest {
    private Boolean isPaid;
    private String typeTransaction;
    private double GPA;
    private String subjectScore;
    private long classroomId;
    private long studentId;
    private long yearId;

    public void requestStudentYear(StudentYear studentYear) {
        studentYear.setIsPaid(this.isPaid);
        studentYear.setTypeTransaction(this.typeTransaction);
        studentYear.setGPA(this.GPA);
        studentYear.setSubjectScore(this.subjectScore);

        Classroom classroom = new Classroom();
        classroom.setId(this.classroomId);

        Student student = new Student();
        student.setId(this.studentId);

        Year year = new Year();
        year.setId(this.yearId);

        studentYear.setClassroom(classroom);
        studentYear.setStudent(student);
        studentYear.setYear(year);
    }

}
