package com.java.asms.services;

import com.java.asms.dtos.dtoGeneration.generationResponse.GenerationResponse;
import com.java.asms.dtos.dtoYear.requestYear.YearRequest;
import com.java.asms.dtos.dtoYear.responseYear.YearResponse;
import com.java.asms.models.Generation;
import com.java.asms.models.Year;
import com.java.asms.repositories.GenerationRepository;
import com.java.asms.repositories.YearRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class YearService {
    private final YearRepository yearRepository;
    private final GenerationRepository generationRepository;
    public YearResponse createYear(YearRequest yearRequest) {
        Year year = new Year();

        year.setName(yearRequest.getName());
        year.setStudentNumber(yearRequest.getStudentNumber());
        year.setExpectedNumber(yearRequest.getExpectedNumber());
        year.setYearStatus(yearRequest.getYearStatus());
        year.setYear(yearRequest.getYear());
        year.setDescription(yearRequest.getDescription());
        year.setStartDate(yearRequest.getStartDate());
        year.setEndDate(yearRequest.getEndDate());

        Generation generation = generationRepository.findById((int) yearRequest.getGenerationId())
                .orElseThrow(() -> new RuntimeException("Generation with ID " + yearRequest.getGenerationId() + " not found"));
        year.setGeneration(generation);

        Year savedYear = yearRepository.save(year);

        YearResponse yearResponse = new YearResponse();
        yearResponse.setId(savedYear.getId());
        yearResponse.setName(savedYear.getName());
        yearResponse.setStudentNumber(savedYear.getStudentNumber());
        yearResponse.setExpectedNumber(savedYear.getExpectedNumber());
        yearResponse.setYearStatus(savedYear.getYearStatus());
        yearResponse.setYear(savedYear.getYear());
        yearResponse.setStartDate(savedYear.getStartDate());
        yearResponse.setEndDate(savedYear.getEndDate());
        yearResponse.setDescription(savedYear.getDescription());


        GenerationResponse generationResponse = new GenerationResponse();
        generationResponse.response(savedYear.getGeneration());
        yearResponse.setGeneration(generationResponse);

        return yearResponse;
    }


    public YearResponse getYearById(long id) {
        Year year = yearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Year with ID " + id + " not found"));

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

        if (year.getGeneration() != null) {
            GenerationResponse generationResponse = new GenerationResponse();
            generationResponse.response(year.getGeneration());
            yearResponse.setGeneration(generationResponse);
        }

        return yearResponse;
    }

    public YearResponse updateYearById(long id, YearRequest yearRequest) {

        Year year = yearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Year with ID " + id + " not found"));

        year.setName(yearRequest.getName());
        year.setStudentNumber(yearRequest.getStudentNumber());
        year.setExpectedNumber(yearRequest.getExpectedNumber());
        year.setYearStatus(yearRequest.getYearStatus());
        year.setYear(yearRequest.getYear());
        year.setDescription(yearRequest.getDescription());
        year.setStartDate(yearRequest.getStartDate());
        year.setEndDate(yearRequest.getEndDate());

        if (yearRequest.getGenerationId() > 0) {  // Assuming generationId is positive if valid
            Generation generation = generationRepository.findById((int) yearRequest.getGenerationId())
                    .orElseThrow(() -> new RuntimeException("Generation with ID " + yearRequest.getGenerationId() + " not found"));
            year.setGeneration(generation);
        }

        Year updatedYear = yearRepository.save(year);

        YearResponse yearResponse = new YearResponse();
        yearResponse.setId(updatedYear.getId());
        yearResponse.setName(updatedYear.getName());
        yearResponse.setStudentNumber(updatedYear.getStudentNumber());
        yearResponse.setExpectedNumber(updatedYear.getExpectedNumber());
        yearResponse.setYearStatus(updatedYear.getYearStatus());
        yearResponse.setYear(updatedYear.getYear());
        yearResponse.setStartDate(updatedYear.getStartDate());
        yearResponse.setEndDate(updatedYear.getEndDate());
        yearResponse.setDescription(updatedYear.getDescription());

        if (updatedYear.getGeneration() != null) {
            GenerationResponse generationResponse = new GenerationResponse();
            generationResponse.response(updatedYear.getGeneration());
            yearResponse.setGeneration(generationResponse);
        }

        return yearResponse;
    }


    public void deleteYearId(long id) {
        Year year = yearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Year with ID " + id + " not found"));
        yearRepository.delete(year);
    }

}
