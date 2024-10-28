package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoMajor.majorRequest.MajorRequest;
import com.java.asms.dtos.dtoMajor.majorResponse.MajorResponse;
import com.java.asms.services.MajorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/major")
public class MajorController {
    private final MajorService majorService;

    @PostMapping("/create/major")
    public ResponseEntity<APIResponse<Object>> createMajor(@RequestBody MajorRequest majorRequest) {
        MajorResponse majorResponse = majorService.createMajor(majorRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create a major by successfully. ")
                .payload(majorResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/majorBy/{id}")
    public ResponseEntity<APIResponse<Object>> getMajorById(@PathVariable long id) {
        try {
            MajorResponse majorResponse = majorService.getMajorById(id);

            if (majorResponse == null) {
                APIResponse<Object> apiResponse = APIResponse.builder()
                        .message("Major with id " + id + " not found.")
                        .status(HttpStatus.NOT_FOUND)
                        .dateTime(LocalDateTime.now())
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Get major by id " + id + " successful.")
                    .payload(majorResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("An error occurred while retrieving major with id " + id + ": " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }



    @PutMapping("/update/majorBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateMajorById(
            @PathVariable long id,
            @RequestBody MajorRequest majorRequest) {
        MajorResponse majorResponse = majorService.updateMajorById(id, majorRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Updated with major id " + id + " successful.")
                .payload(majorResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/majorBy/{id}")
    public ResponseEntity<APIDeResponse> deleteMajorById(@PathVariable long id) {
        try {
            majorService.deleteMajorById(id);

            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Deleted major with id " + id + " successfully.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();

            return ResponseEntity.ok(apiDeResponse);
        } catch (RuntimeException e) {
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiDeResponse);
        }
    }
}

