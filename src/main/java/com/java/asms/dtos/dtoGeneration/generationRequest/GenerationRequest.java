package com.java.asms.dtos.dtoGeneration.generationRequest;

import com.java.asms.enums.GenerationStatus;
import com.java.asms.models.Generation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenerationRequest {
    private int expectedNumber;
    private long majorId;
    private GenerationStatus generationStatus;
    public void request(Generation generation) {
        this.expectedNumber = generation.getExpectedNumber();
        this.majorId = generation.getMajor().getId();
        this.generationStatus = generation.getGenerationStatus();
    }
}

