package com.java.asms.services;

import com.java.asms.dtos.dtoStudent.requestStudent.StudentRequest;
import com.java.asms.dtos.dtoStudent.responseStudent.StudentResponse;
import com.java.asms.models.Student;
import com.java.asms.repositories.StudentRepository;
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
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentResponse createStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setDob(studentRequest.getDob());
        student.setEmail(studentRequest.getEmail());
        student.setAddress(studentRequest.getAddress());
        student.setPhone(studentRequest.getPhone());
        student.setGuardianPhone(studentRequest.getGuardianPhone());
        student.setParentPhone(studentRequest.getParentPhone());
        student.setStudentStatus(studentRequest.getStudentStatus());

        Student savedStudent = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(savedStudent.getId());
        studentResponse.setFirstName(savedStudent.getFirstName());
        studentResponse.setLastName(savedStudent.getLastName());
        studentResponse.setDob(savedStudent.getDob());
        studentResponse.setStudentStatus(savedStudent.getStudentStatus());
        studentResponse.setAddress(savedStudent.getAddress());
        studentResponse.setPhone(savedStudent.getPhone());
        studentResponse.setEmail(savedStudent.getEmail());
        studentResponse.setGuardianPhone(savedStudent.getGuardianPhone());
        studentResponse.setParentPhone(savedStudent.getParentPhone());

        return studentResponse;
    }


    public StudentResponse getStudentById(long id) {
        Student student = studentRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setFirstName(student.getFirstName());
        studentResponse.setLastName(student.getLastName());
        studentResponse.setDob(student.getDob());
        studentResponse.setStudentStatus(student.getStudentStatus());
        studentResponse.setAddress(student.getAddress());
        studentResponse.setPhone(student.getPhone());
        studentResponse.setEmail(student.getEmail());
        studentResponse.setGuardianPhone(student.getGuardianPhone());
        studentResponse.setParentPhone(student.getParentPhone());

        return studentResponse;
    }

    public StudentResponse updateStudentById(long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found."));

        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setDob(studentRequest.getDob());
        student.setEmail(studentRequest.getEmail());
        student.setAddress(studentRequest.getAddress());
        student.setPhone(studentRequest.getPhone());
        student.setGuardianPhone(studentRequest.getGuardianPhone());
        student.setParentPhone(studentRequest.getParentPhone());
        student.setStudentStatus(studentRequest.getStudentStatus());

        student = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setFirstName(student.getFirstName());
        studentResponse.setLastName(student.getLastName());
        studentResponse.setDob(student.getDob());
        studentResponse.setStudentStatus(student.getStudentStatus());
        studentResponse.setAddress(student.getAddress());
        studentResponse.setPhone(student.getPhone());
        studentResponse.setEmail(student.getEmail());
        studentResponse.setGuardianPhone(student.getGuardianPhone());
        studentResponse.setParentPhone(student.getParentPhone());

        return studentResponse;
    }

    public void deleteStudentById(long id) {
        Student student = studentRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found."));
        studentRepository.delete(student);
    }

    public List<StudentResponse> getAllStudent(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Student> studentPage = studentRepository.findAll(pageable);
        List<Student> studentList = studentPage.getContent();

        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (Student student: studentList){
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(student.getId());
            studentResponse.setFirstName(student.getFirstName());
            studentResponse.setLastName(student.getLastName());
            studentResponse.setDob(student.getDob());
            studentResponse.setStudentStatus(student.getStudentStatus());
            studentResponse.setAddress(student.getAddress());
            studentResponse.setPhone(student.getPhone());
            studentResponse.setEmail(student.getEmail());
            studentResponse.setGuardianPhone(student.getGuardianPhone());
            studentResponse.setParentPhone(student.getParentPhone());
            studentResponseList.add(studentResponse);
        }
        return studentResponseList;
    }
}
