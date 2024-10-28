package com.java.asms.dtos.dtoGeneration.generationResponse;

import com.java.asms.dtos.dtoMajor.majorResponse.GenerationMajorResponse;
import com.java.asms.dtos.dtoMajor.majorResponse.MajorResponse;
import com.java.asms.enums.GenerationStatus;
import com.java.asms.models.Generation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenerationResponse {
    private long id;
    private int expectedNumber;
    private GenerationMajorResponse major;
    private GenerationStatus generationStatus;
    private Date createdAt;
    private Date updatedAt;

    public void response(Generation generation) {
        this.id = generation.getId();
        this.expectedNumber = generation.getExpectedNumber();
        this.generationStatus = generation.getGenerationStatus();
        this.createdAt = generation.getCreatedAt();
        this.updatedAt = generation.getUpdatedAt();
        if (generation.getMajor() != null) {
            GenerationMajorResponse majorResponse = new GenerationMajorResponse();
            majorResponse.responseMajor(generation.getMajor());
            this.major = majorResponse;
        }
    }

}
