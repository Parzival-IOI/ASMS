package com.java.asms.services;

import com.java.asms.dtos.dtoGeneration.generationResponse.GenerationResponse;
import com.java.asms.dtos.dtoMajor.majorResponse.GenerationMajorResponse;
import com.java.asms.dtos.dtoRegisterYear.request.RegisterYearRequest;
import com.java.asms.dtos.dtoRegisterYear.response.RegisterYearResponse;
import com.java.asms.dtos.dtoStudent.responseStudent.StudentResponse;
import com.java.asms.dtos.dtoYear.responseYear.YearResponse;
import com.java.asms.models.Generation;
import com.java.asms.models.RegisterYear;
import com.java.asms.models.Student;
import com.java.asms.models.Year;
import com.java.asms.repositories.RegisterYearRepository;
import com.java.asms.repositories.StudentRepository;
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
public class RegisterYearService {
    private final RegisterYearRepository registerYearRepository;
    private final StudentRepository studentRepository;
    private final YearRepository yearRepository;

    public RegisterYearResponse createYearRegister(RegisterYearRequest registerYearRequest) {
        RegisterYear registerYear = new RegisterYear();

        registerYear.setDateExam(registerYearRequest.getDateExam());
        registerYear.setScore(registerYearRequest.getScore());
        registerYear.setIsPassed(registerYearRequest.getIsPassed());

        Student student = studentRepository.findById((int) registerYearRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student with ID " + registerYearRequest.getStudentId() + " not found."));
        Year year = yearRepository.findById((int) registerYearRequest.getYearId())
                .orElseThrow(() -> new RuntimeException("Year with ID " + registerYearRequest.getYearId() + " not found."));

        registerYear.setStudent(student);
        registerYear.setYear(year);

        RegisterYear savedRegisterYear = registerYearRepository.save(registerYear);

        RegisterYearResponse registerYearResponse = new RegisterYearResponse();
        registerYearResponse.setId(savedRegisterYear.getId());
        registerYearResponse.setIsPassed(savedRegisterYear.getIsPassed());
        registerYearResponse.setScore(savedRegisterYear.getScore());
        registerYearResponse.setDateExam(savedRegisterYear.getDateExam());

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

        YearResponse yearResponse = new YearResponse();
        yearResponse.setId(year.getId());
        yearResponse.setName(year.getName());
        yearResponse.setStudentNumber(year.getStudentNumber());
        yearResponse.setExpectedNumber(year.getExpectedNumber());
        yearResponse.setYearStatus(year.getYearStatus());
        yearResponse.setYear(year.getYear());
        yearResponse.setStartDate(year.getStartDate());
        yearResponse.setEndDate(year.getEndDate());
        yearResponse.setDescription(year.getDescription());

        Generation generation = year.getGeneration();
        GenerationResponse generationResponse = convertToGenerationResponse(generation);
        yearResponse.setGeneration(generationResponse);

        registerYearResponse.setStudent(studentResponse);
        registerYearResponse.setYear(yearResponse);

        return registerYearResponse;
    }

    // dtp generation response
    private GenerationResponse convertToGenerationResponse(Generation generation) {
        GenerationResponse generationResponse = new GenerationResponse();
        if (generation != null) {
            generationResponse.setId(generation.getId());
            generationResponse.setExpectedNumber(generation.getExpectedNumber());
            generationResponse.setGenerationStatus(generation.getGenerationStatus());
            generationResponse.setCreatedAt(generation.getCreatedAt());
            generationResponse.setUpdatedAt(generation.getUpdatedAt());

            if (generation.getMajor() != null) {
                GenerationMajorResponse majorResponse = new GenerationMajorResponse();
                majorResponse.responseMajor(generation.getMajor()); // Ensure you have a method to set this data
                generationResponse.setMajor(majorResponse);
            }
        }
        return generationResponse;
    }

    public RegisterYearResponse getRegisterYearById(long id) {
        RegisterYear registerYear = registerYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Register Year with ID " + id + " not found."));

        RegisterYearResponse registerYearResponse = new RegisterYearResponse();
        registerYearResponse.setId(registerYear.getId());
        registerYearResponse.setIsPassed(registerYear.getIsPassed());
        registerYearResponse.setScore(registerYear.getScore());
        registerYearResponse.setDateExam(registerYear.getDateExam());

        Student student = registerYear.getStudent();
        if (student != null) {
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
            registerYearResponse.setStudent(studentResponse);
        }

        Year year = registerYear.getYear();
        if (year != null) {
            YearResponse yearResponse = new YearResponse();
            yearResponse.setId(year.getId());
            yearResponse.setName(year.getName());
            yearResponse.setStudentNumber(year.getStudentNumber());
            yearResponse.setExpectedNumber(year.getExpectedNumber());
            yearResponse.setYearStatus(year.getYearStatus());
            yearResponse.setYear(year.getYear());
            yearResponse.setStartDate(year.getStartDate());
            yearResponse.setEndDate(year.getEndDate());
            yearResponse.setDescription(year.getDescription());

            Generation generation = year.getGeneration();
            if (generation != null) {
                GenerationResponse generationResponse = convertToGenerationResponse(generation);
                yearResponse.setGeneration(generationResponse);
            }

            registerYearResponse.setYear(yearResponse);
        }

        return registerYearResponse;
    }

    public RegisterYearResponse updateRegisterYearById(long id, RegisterYearRequest registerYearRequest) {
        RegisterYear registerYear = registerYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Register Year with ID " + id + " not found."));

        registerYear.setDateExam(registerYearRequest.getDateExam());
        registerYear.setScore(registerYearRequest.getScore());
        registerYear.setIsPassed(registerYearRequest.getIsPassed());

        Student student = studentRepository.findById((int) registerYearRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student with ID " + registerYearRequest.getStudentId() + " not found."));
        registerYear.setStudent(student);

        Year year = yearRepository.findById((int) registerYearRequest.getYearId())
                .orElseThrow(() -> new RuntimeException("Year with ID " + registerYearRequest.getYearId() + " not found."));
        registerYear.setYear(year);

        RegisterYear updatedRegisterYear = registerYearRepository.save(registerYear);

        RegisterYearResponse registerYearResponse = new RegisterYearResponse();
        registerYearResponse.setId(updatedRegisterYear.getId());
        registerYearResponse.setIsPassed(updatedRegisterYear.getIsPassed());
        registerYearResponse.setScore(updatedRegisterYear.getScore());
        registerYearResponse.setDateExam(updatedRegisterYear.getDateExam());

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
        registerYearResponse.setStudent(studentResponse);

        YearResponse yearResponse = new YearResponse();
        yearResponse.setId(year.getId());
        yearResponse.setName(year.getName());
        yearResponse.setStudentNumber(year.getStudentNumber());
        yearResponse.setExpectedNumber(year.getExpectedNumber());
        yearResponse.setYearStatus(year.getYearStatus());
        yearResponse.setYear(year.getYear());
        yearResponse.setStartDate(year.getStartDate());
        yearResponse.setEndDate(year.getEndDate());
        yearResponse.setDescription(year.getDescription());

        Generation generation = year.getGeneration();
        if (generation != null) {
            GenerationResponse generationResponse = convertToGenerationResponse(generation);
            yearResponse.setGeneration(generationResponse);
        }

        registerYearResponse.setYear(yearResponse);

        return registerYearResponse;
    }


    public void deleteRegisterYearById(long id) {
        RegisterYear registerYear = registerYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Register Year with ID " + id + " not found."));

        registerYearRepository.delete(registerYear);
    }

    public List<RegisterYearResponse> getAllRegisterYear(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<RegisterYear> registerYearPage = registerYearRepository.findAll(pageable);
        List<RegisterYear> registerYearList = registerYearPage.getContent();
        List<RegisterYearResponse> registerYearResponseList = new ArrayList<>();

        for (RegisterYear registerYear : registerYearList) {
            RegisterYearResponse registerYearResponse = new RegisterYearResponse();
            registerYearResponse.setId(registerYear.getId());
            registerYearResponse.setIsPassed(registerYear.getIsPassed());
            registerYearResponse.setScore(registerYear.getScore());
            registerYearResponse.setDateExam(registerYear.getDateExam());

            Student student = registerYear.getStudent();
            if (student != null) {
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
                registerYearResponse.setStudent(studentResponse);
            }

            Year year = registerYear.getYear();
            if (year != null) {
                YearResponse yearResponse = new YearResponse();
                yearResponse.setId(year.getId());
                yearResponse.setName(year.getName());
                yearResponse.setStudentNumber(year.getStudentNumber());
                yearResponse.setExpectedNumber(year.getExpectedNumber());
                yearResponse.setYearStatus(year.getYearStatus());
                yearResponse.setYear(year.getYear());
                yearResponse.setStartDate(year.getStartDate());
                yearResponse.setEndDate(year.getEndDate());
                yearResponse.setDescription(year.getDescription());

                Generation generation = year.getGeneration();
                if (generation != null) {
                    GenerationResponse generationResponse = convertToGenerationResponse(generation);
                    yearResponse.setGeneration(generationResponse);
                }
                registerYearResponse.setYear(yearResponse);
            }
            registerYearResponseList.add(registerYearResponse);
        }
        return registerYearResponseList;
    }
}
