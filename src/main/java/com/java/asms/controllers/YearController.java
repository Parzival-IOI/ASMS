package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoYear.requestYear.YearRequest;
import com.java.asms.dtos.dtoYear.responseYear.YearResponse;
import com.java.asms.services.YearService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/year")
public class YearController {
    private final YearService yearService;

    @PostMapping("/create/year")
    public ResponseEntity<APIResponse<Object>> createYear(@RequestBody YearRequest yearRequest){
        YearResponse yearResponse = yearService.createYear(yearRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create year successful .")
                .payload(yearResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/yearBy/{id}")
    public ResponseEntity<APIResponse<Object>> getYearById(@PathVariable long id){
        YearResponse yearResponse = yearService.getYearById(id);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get year by id successful .")
                .payload(yearResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/yearBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateYearById(
            @PathVariable long id,
            @RequestBody YearRequest yearRequest){
        YearResponse yearResponse = yearService.updateYearById(id,yearRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Update year with id "+id+"successful .")
                .payload(yearResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/yearById/{id}")
    public ResponseEntity<APIDeResponse> deleteYearById(@PathVariable long id){
        yearService.deleteYearId(id);
        APIDeResponse apiDeResponse = APIDeResponse.builder()
                .message("Delete year with id "+id+"successful .")
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiDeResponse);
    }


}
