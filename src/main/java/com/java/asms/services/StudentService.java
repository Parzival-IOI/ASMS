package com.java.asms.services;

import com.java.asms.dtos.dtoStudent.requestStudent.StudentRequest;
import com.java.asms.dtos.dtoStudent.responseStudent.StudentResponse;
import com.java.asms.enums.LoginStatus;
import com.java.asms.models.Login;
import com.java.asms.models.Student;
import com.java.asms.repositories.LoginRepository;
import com.java.asms.repositories.StudentRepository;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final LoginRepository loginRepository;


    public StudentResponse createStudent(StudentRequest studentRequest) {

        Student student = Student.builder()
                        .firstName(studentRequest.getFirstName())
                        .lastName(studentRequest.getLastName())
                        .dob(studentRequest.getDob())
                        .email(studentRequest.getEmail())
                        .address(studentRequest.getAddress())
                        .phone(studentRequest.getPhone())
                        .guardianPhone(studentRequest.getGuardianPhone())
                        .parentPhone(studentRequest.getParentPhone())
                        .studentStatus(studentRequest.getStudentStatus())
                        .build();
        Student saved = studentRepository.save(student);

        Login login = loginRepository.save(
                Login.builder()
                        .username(studentRequest.getUsername())
                        .password(new BCryptPasswordEncoder().encode(studentRequest.getPassword()))
                        .isStudent(true)
                        .isBlocked(false)
                        .attempt(0)
                        .status(LoginStatus.ENABLE)
                        .student(student)
                        .build()
        );

        return StudentResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .username(login.getUsername())
                .dob(saved.getDob())
                .email(saved.getEmail())
                .address(saved.getAddress())
                .phone(saved.getPhone())
                .guardianPhone(saved.getGuardianPhone())
                .parentPhone(saved.getParentPhone())
                .studentStatus(saved.getStudentStatus())
                .build();
    }


    public StudentResponse getStudentById(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));

        return studentResponseMapping(student);
    }

    public StudentResponse updateStudentById(long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
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



        Login login = student.getLogin();
        login.setUsername(studentRequest.getUsername());
        if(!studentRequest.getPassword().isEmpty()) {
            login.setPassword(new BCryptPasswordEncoder().encode(studentRequest.getPassword()));
        }

        loginRepository.save(login);

        studentRepository.save(student);

        Student saved2 = studentRepository.findById(student.getId())
                .orElseThrow(() -> new RuntimeException("Student with id " + student.getId() + " not found"));

        return studentResponseMapping(saved2);
    }

    public void deleteStudentById(long id) {
        Student student = studentRepository.findById( id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found."));
        loginRepository.delete(student.getLogin());
        studentRepository.delete(student);
    }

    public List<StudentResponse> getAllStudent(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Student> studentPage = studentRepository.findAll(pageable);
        List<Student> studentList = studentPage.getContent();

        return studentList.stream().map(this::studentResponseMapping).toList();

    }

    private StudentResponse studentResponseMapping(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .username(student.getLogin().getUsername())
                .dob(student.getDob())
                .email(student.getEmail())
                .address(student.getAddress())
                .phone(student.getPhone())
                .guardianPhone(student.getGuardianPhone())
                .parentPhone(student.getParentPhone())
                .studentStatus(student.getStudentStatus())
                .build();
    }
}
