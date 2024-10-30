package com.java.asms.dtos.dtoStudentYear.response;

import com.java.asms.dtos.dtoClassroom.response.ClassroomResponse;
import com.java.asms.dtos.dtoStudent.responseStudent.StudentResponse;
import com.java.asms.dtos.dtoYear.responseYear.YearResponse;
import com.java.asms.models.StudentYear;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentYearResponse {
    private long id;
    private Boolean isPaid;
    private String typeTransaction;
    private double GPA;
    private String subjectScore;
    private ClassroomResponse classroom;
    private StudentResponse student;
    private YearResponse year;
    public void responseStudentYear(StudentYear studentYear) {
        this.id = studentYear.getId();
        this.isPaid = studentYear.getIsPaid();
        this.typeTransaction = studentYear.getTypeTransaction();
        this.GPA = studentYear.getGPA();
        this.subjectScore = studentYear.getSubjectScore();

        if (studentYear.getClassroom() != null) {
            ClassroomResponse classroomResponse = new ClassroomResponse();
            classroomResponse.responseClassroom(studentYear.getClassroom());
            this.classroom = classroomResponse;
        }

        if (studentYear.getStudent() != null) {
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.responseStudent(studentYear.getStudent());
            this.student = studentResponse;
        }

        if (studentYear.getYear() != null) {
            YearResponse yearResponse = new YearResponse();
            yearResponse.responseYear(studentYear.getYear());
            this.year = yearResponse;
        }
    }

}
