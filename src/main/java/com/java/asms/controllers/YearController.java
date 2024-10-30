package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoMajor.majorResponse.MajorResponse;
import com.java.asms.dtos.dtoYear.requestYear.YearRequest;
import com.java.asms.dtos.dtoYear.responseYear.YearResponse;
import com.java.asms.dtos.dtoYearSubject.yearSubjectResponse.YearSubjectResponse;
import com.java.asms.services.YearService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<APIResponse<Object>> getYearById(@PathVariable long id) {
        try {
            YearResponse yearResponse = yearService.getYearById(id);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Get year by id successful.")
                    .payload(yearResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(APIResponse.builder()
                            .message("Year with ID " + id + " not found.")
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.builder()
                            .message("An error occurred while retrieving the year.")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @PutMapping("/update/yearBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateYearById(
            @PathVariable long id,
            @RequestBody YearRequest yearRequest) {
        try {
            YearResponse yearResponse = yearService.updateYearById(id, yearRequest);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Update year with id " + id + " successful.")
                    .payload(yearResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(APIResponse.builder()
                            .message("Year with ID " + id + " not found.")
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.builder()
                            .message("An error occurred while updating the year.")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @DeleteMapping("/delete/yearBy/{id}")
    public ResponseEntity<APIDeResponse> deleteYearById(@PathVariable long id) {
        try {
            yearService.deleteYearId(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete year with id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(APIDeResponse.builder()
                            .message("Year with ID " + id + " not found.")
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIDeResponse.builder()
                            .message("An error occurred while deleting the year.")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @GetMapping("/getAll/year")
    public ResponseEntity<APIResponse<Object>> getAllYear(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<YearResponse> yearResponseList = yearService.getAllYear(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get all successful .")
                .payload(yearResponseList)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
