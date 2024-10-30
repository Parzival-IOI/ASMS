package com.java.asms.services;

import com.java.asms.dtos.dtoGeneration.generationRequest.GenerationRequest;
import com.java.asms.dtos.dtoGeneration.generationResponse.GenerationResponse;
import com.java.asms.models.Generation;
import com.java.asms.models.Major;
import com.java.asms.repositories.GenerationRepository;
import com.java.asms.repositories.MajorRepository;
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
public class GenerationService {
    private final GenerationRepository generationRepository;
    private final MajorRepository majorRepository;

    public GenerationResponse createGeneration(GenerationRequest generationRequest) {
        Major major = majorRepository.findById((int) generationRequest.getMajorId())
                .orElseThrow(() -> new RuntimeException("Major not found"));

        Generation generation = new Generation();
        generation.setExpectedNumber(generationRequest.getExpectedNumber());
        generation.setMajor(major);
        generation.setGenerationStatus(generationRequest.getGenerationStatus());

        Generation savedGeneration = generationRepository.save(generation);

        GenerationResponse generationResponse = new GenerationResponse();
        generationResponse.response(savedGeneration);
        return generationResponse;
    }

    public GenerationResponse getGenerationById(long id) {
        Generation generation = generationRepository.findById((int)id)
                .orElseThrow(() -> new RuntimeException("Generation not found"));

        GenerationResponse generationResponse = new GenerationResponse();
        generationResponse.response(generation);
        return generationResponse;
    }

    public GenerationResponse updateGenerationById(long id, GenerationRequest generationRequest) {
        Generation generation = generationRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Generation with ID " + id + " not found"));
        generation.setExpectedNumber(generationRequest.getExpectedNumber());

        Major major = majorRepository.findById((int) generationRequest.getMajorId())
                .orElseThrow(() -> new RuntimeException("Major with ID " + generationRequest.getMajorId() + " not found"));
        generation.setMajor(major);

        generation.setGenerationStatus(generationRequest.getGenerationStatus());

        Generation updatedGeneration = generationRepository.save(generation);

        GenerationResponse generationResponse = new GenerationResponse();
        generationResponse.response(updatedGeneration);
        return generationResponse;
    }

    public void deleteGenerationById(long id) {
        Generation generation = generationRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Generation not found with id: " + id));

        generationRepository.delete(generation);
    }


    public List<GenerationResponse> getAllGeneration(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Generation> generationPage = generationRepository.findAll(pageable);
        List<Generation> generationList = generationPage.getContent();
        List<GenerationResponse> generationResponseList = new ArrayList<>();
        for (Generation generation: generationList){
            GenerationResponse generationResponse = new GenerationResponse();
            generationResponse.response(generation);
            generationResponseList.add(generationResponse);
        }
        return generationResponseList;
    }
}
