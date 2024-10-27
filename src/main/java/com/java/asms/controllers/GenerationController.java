package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoGeneration.generationRequest.GenerationRequest;
import com.java.asms.dtos.dtoGeneration.generationResponse.GenerationResponse;
import com.java.asms.services.GenerationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/generation")
@AllArgsConstructor
public class GenerationController {
    private final GenerationService generationService;

    @PostMapping("/create/generation")
    public ResponseEntity<APIResponse<Object>> createGeneration(@RequestBody GenerationRequest generationRequest){
        GenerationResponse generationResponse = generationService.createGeneration(generationRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create generation successful .")
                .payload(generationResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/generationById/{id}")
    public ResponseEntity<APIResponse<Object>> getGenerationById(@PathVariable long id){
        GenerationResponse generationResponse = generationService.getGenerationById(id);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get generation id "+id+" successful .")
                .payload(generationResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/generationBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateGenerationById(
            @PathVariable long id,
            @RequestBody GenerationRequest generationRequest){
        GenerationResponse generationResponse = generationService.updateGenerationById(id,generationRequest);
        APIResponse<Object>  apiResponse = APIResponse.builder()
                .message("Updated generation with id "+id+" successful.")
                .payload(generationResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/generationBy/{id}")
    public ResponseEntity<APIDeResponse> deleteGenerationById(@PathVariable long id) {
            generationService.deleteGenerationById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete generation with id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
    }

}
