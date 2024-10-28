package com.java.asms.services;

import com.java.asms.dtos.dtoSubject.subjectResponse.DTOSubjectYearResponse;
import com.java.asms.dtos.dtoYear.responseYear.DTOYearResponse;
import com.java.asms.dtos.dtoYearSubject.yearSubjectRequest.YearSubjectRequest;
import com.java.asms.dtos.dtoYearSubject.yearSubjectResponse.YearSubjectResponse;
import com.java.asms.models.Subject;
import com.java.asms.models.SubjectYear;
import com.java.asms.models.Year;
import com.java.asms.repositories.SubjectRepository;
import com.java.asms.repositories.SubjectYearRepository;
import com.java.asms.repositories.YearRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectYearService {
    private final SubjectYearRepository subjectYearRepository;
    private final SubjectRepository subjectRepository;
    private final YearRepository yearRepository;

    public YearSubjectResponse createYearSubject(YearSubjectRequest yearSubjectRequest) {
        Subject subject = subjectRepository.findById((int) yearSubjectRequest.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject with ID " + yearSubjectRequest.getSubjectId() + " not found"));

        Year year = yearRepository.findById((int) yearSubjectRequest.getYearId())
                .orElseThrow(() -> new RuntimeException("Year with ID " + yearSubjectRequest.getYearId() + " not found"));

        SubjectYear subjectYear = new SubjectYear();
        subjectYear.setSubject(subject);
        subjectYear.setYear(year);

        SubjectYear savedSubjectYear = subjectYearRepository.save(subjectYear);

        YearSubjectResponse yearSubjectResponse = new YearSubjectResponse();
        yearSubjectResponse.setId(savedSubjectYear.getId());

        DTOSubjectYearResponse dtoSubjectYearResponse = new DTOSubjectYearResponse();
        dtoSubjectYearResponse.setId(subject.getId());
        dtoSubjectYearResponse.setYear(subject.getYear());
        dtoSubjectYearResponse.setName(subject.getName());
        dtoSubjectYearResponse.setDescription(subject.getDescription());
        yearSubjectResponse.setSubject(dtoSubjectYearResponse);

        DTOYearResponse dtoYearResponse = new DTOYearResponse();
        dtoYearResponse.setId(year.getId());
        dtoYearResponse.setName(year.getName());
        dtoYearResponse.setStudentNumber(year.getStudentNumber());
        dtoYearResponse.setExpectedNumber(year.getExpectedNumber());
        dtoYearResponse.setYearStatus(year.getYearStatus());
        dtoYearResponse.setYear(year.getYear());
        dtoYearResponse.setStartDate(year.getStartDate());
        dtoYearResponse.setEndDate(year.getEndDate());
        dtoYearResponse.setDescription(year.getDescription());
        yearSubjectResponse.setYear(dtoYearResponse);

        return yearSubjectResponse;
    }

    public YearSubjectResponse getYearSubjectById(long id) {
        SubjectYear subjectYear = subjectYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("SubjectYear with ID " + id + " not found"));

        YearSubjectResponse yearSubjectResponse = new YearSubjectResponse();
        yearSubjectResponse.setId(subjectYear.getId());

        DTOSubjectYearResponse dtoSubjectYearResponse = new DTOSubjectYearResponse();
        dtoSubjectYearResponse.setId(subjectYear.getSubject().getId());
        dtoSubjectYearResponse.setYear(subjectYear.getSubject().getYear());
        dtoSubjectYearResponse.setName(subjectYear.getSubject().getName());
        dtoSubjectYearResponse.setDescription(subjectYear.getSubject().getDescription());
        yearSubjectResponse.setSubject(dtoSubjectYearResponse);

        DTOYearResponse dtoYearResponse = new DTOYearResponse();
        dtoYearResponse.setId(subjectYear.getYear().getId());
        dtoYearResponse.setName(subjectYear.getYear().getName());
        dtoYearResponse.setStudentNumber(subjectYear.getYear().getStudentNumber());
        dtoYearResponse.setExpectedNumber(subjectYear.getYear().getExpectedNumber());
        dtoYearResponse.setYearStatus(subjectYear.getYear().getYearStatus());
        dtoYearResponse.setYear(subjectYear.getYear().getYear());
        dtoYearResponse.setStartDate(subjectYear.getYear().getStartDate());
        dtoYearResponse.setEndDate(subjectYear.getYear().getEndDate());
        dtoYearResponse.setDescription(subjectYear.getYear().getDescription());
        yearSubjectResponse.setYear(dtoYearResponse);

        return yearSubjectResponse;
    }


    public YearSubjectResponse updateSubjectYearById(long id, YearSubjectRequest yearSubjectRequest) {
        SubjectYear subjectYear = subjectYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("SubjectYear with ID " + id + " not found"));

        Subject subject = subjectRepository.findById((int) yearSubjectRequest.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject with ID " + yearSubjectRequest.getSubjectId() + " not found"));

        Year year = yearRepository.findById((int) yearSubjectRequest.getYearId())
                .orElseThrow(() -> new RuntimeException("Year with ID " + yearSubjectRequest.getYearId() + " not found"));

        subjectYear.setSubject(subject);
        subjectYear.setYear(year);

        SubjectYear updatedSubjectYear = subjectYearRepository.save(subjectYear);

        YearSubjectResponse yearSubjectResponse = new YearSubjectResponse();
        yearSubjectResponse.setId(updatedSubjectYear.getId());

        DTOSubjectYearResponse dtoSubjectYearResponse = new DTOSubjectYearResponse();
        dtoSubjectYearResponse.setId(updatedSubjectYear.getSubject().getId());
        dtoSubjectYearResponse.setYear(updatedSubjectYear.getSubject().getYear());
        dtoSubjectYearResponse.setName(updatedSubjectYear.getSubject().getName());
        dtoSubjectYearResponse.setDescription(updatedSubjectYear.getSubject().getDescription());
        yearSubjectResponse.setSubject(dtoSubjectYearResponse);

        DTOYearResponse dtoYearResponse = new DTOYearResponse();
        dtoYearResponse.setId(updatedSubjectYear.getYear().getId());
        dtoYearResponse.setName(updatedSubjectYear.getYear().getName());
        dtoYearResponse.setStudentNumber(updatedSubjectYear.getYear().getStudentNumber());
        dtoYearResponse.setExpectedNumber(updatedSubjectYear.getYear().getExpectedNumber());
        dtoYearResponse.setYearStatus(updatedSubjectYear.getYear().getYearStatus());
        dtoYearResponse.setYear(updatedSubjectYear.getYear().getYear());
        dtoYearResponse.setStartDate(updatedSubjectYear.getYear().getStartDate());
        dtoYearResponse.setEndDate(updatedSubjectYear.getYear().getEndDate());
        dtoYearResponse.setDescription(updatedSubjectYear.getYear().getDescription());
        yearSubjectResponse.setYear(dtoYearResponse);

        return yearSubjectResponse;
    }


    public void deleteSubjectYearById(long id) {
        SubjectYear subjectYear = subjectYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("SubjectYear with ID " + id + " not found"));
        subjectYearRepository.delete(subjectYear);
    }

}
