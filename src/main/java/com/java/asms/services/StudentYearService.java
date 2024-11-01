package com.java.asms.services;

import com.java.asms.dtos.dtoClassroom.response.ClassroomResponse;
import com.java.asms.dtos.dtoStudentYear.request.StudentYearRequest;
import com.java.asms.dtos.dtoStudentYear.response.StudentYearResponse;
import com.java.asms.models.*;
import com.java.asms.repositories.ClassroomRepository;
import com.java.asms.repositories.StudentRepository;
import com.java.asms.repositories.StudentYearRepository;
import com.java.asms.repositories.YearRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentYearService {
    private final StudentYearRepository studentYearRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final YearRepository yearRepository;
    public StudentYearResponse createStudentYear(StudentYearRequest studentYearRequest) {
        Classroom classroom = classroomRepository.findById((int) studentYearRequest.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + studentYearRequest.getClassroomId()));

        Student student = studentRepository.findById(studentYearRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentYearRequest.getStudentId()));

        Year year = yearRepository.findById((int) studentYearRequest.getYearId())
                .orElseThrow(() -> new RuntimeException("Year not found with ID: " + studentYearRequest.getYearId()));

        StudentYear studentYear = new StudentYear();
        studentYearRequest.requestStudentYear(studentYear);
        studentYear.setClassroom(classroom);
        studentYear.setStudent(student);
        studentYear.setYear(year);

        StudentYear savedStudentYear = studentYearRepository.save(studentYear);
        StudentYearResponse studentYearResponse = new StudentYearResponse();
        studentYearResponse.responseStudentYear(savedStudentYear);
        return studentYearResponse;
    }


    public StudentYearResponse getStudentYearById(long id) {
        StudentYear studentYear = studentYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("StudentYear with ID " + id + " not found."));

        StudentYearResponse studentYearResponse = new StudentYearResponse();

        studentYearResponse.responseStudentYear(studentYear);

        return studentYearResponse;
    }

    public StudentYearResponse updateStudentYearById(long id, StudentYearRequest studentYearRequest) {
        StudentYear studentYear = studentYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("StudentYear with ID " + id + " not found."));

        Classroom classroom = classroomRepository.findById((int) studentYearRequest.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + studentYearRequest.getClassroomId()));

        Student student = studentRepository.findById(studentYearRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentYearRequest.getStudentId()));

        Year year = yearRepository.findById((int) studentYearRequest.getYearId())
                .orElseThrow(() -> new RuntimeException("Year not found with ID: " + studentYearRequest.getYearId()));

        studentYear.setIsPaid(studentYearRequest.getIsPaid());
        studentYear.setTypeTransaction(studentYearRequest.getTypeTransaction());
        studentYear.setGPA(studentYearRequest.getGPA());
        studentYear.setSubjectScore(studentYearRequest.getSubjectScore());
        studentYear.setClassroom(classroom);
        studentYear.setStudent(student);
        studentYear.setYear(year);

        StudentYear updatedStudentYear = studentYearRepository.save(studentYear);

        StudentYearResponse studentYearResponse = new StudentYearResponse();
        studentYearResponse.responseStudentYear(updatedStudentYear);

        return studentYearResponse;
    }


    public void deleteStudentYearById(long id) {
        StudentYear studentYear = studentYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("StudentYear with ID " + id + " not found."));
        studentYearRepository.delete(studentYear);
    }

    public List<StudentYearResponse> getAllStudentYear(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<StudentYear> studentYearPage = studentYearRepository.findAll(pageable);
        List<StudentYear> studentYearList = studentYearPage.getContent();
        List<StudentYearResponse> studentYearResponseList = new ArrayList<>();
        for (StudentYear studentYear: studentYearList){
            StudentYearResponse studentYearResponse = new StudentYearResponse();
            studentYearResponse.responseStudentYear(studentYear);
            studentYearResponseList.add(studentYearResponse);
        }
        return studentYearResponseList;
    }
}
