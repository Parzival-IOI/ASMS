package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoYearSubject.yearSubjectRequest.YearSubjectRequest;
import com.java.asms.dtos.dtoYearSubject.yearSubjectResponse.YearSubjectResponse;
import com.java.asms.services.SubjectYearService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject-year")
public class SubjectYearController {
    private final SubjectYearService subjectYearService;

    @PostMapping("/create/subject-year")
    public ResponseEntity<APIResponse<Object>> createYearSubject(@RequestBody YearSubjectRequest yearSubjectRequest){
        YearSubjectResponse yearSubjectResponse = subjectYearService.createYearSubject(yearSubjectRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create year of subject successful .")
                .payload(yearSubjectResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/subject-yearBy/{id}")
    public ResponseEntity<APIResponse<Object>> getYearSubjectById(@PathVariable long id) {
        try {
            YearSubjectResponse yearSubjectResponse = subjectYearService.getYearSubjectById(id);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Get year of subject by id " + id + " successful.")
                    .payload(yearSubjectResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(APIResponse.builder()
                            .message("Subject year with ID " + id + " not found.")
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @PutMapping("/update/subject-yearBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateSubjectYearById(@PathVariable long id, @RequestBody YearSubjectRequest yearSubjectRequest) {
        try {
            YearSubjectResponse yearSubjectResponse = subjectYearService.updateSubjectYearById(id, yearSubjectRequest);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Update subject of year with id " + id + " successful.")
                    .payload(yearSubjectResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(APIResponse.builder()
                            .message("Subject year with ID " + id + " not found.")
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.builder()
                            .message("An error occurred while updating the subject year.")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @DeleteMapping("/delete/subject-yearBy/{id}")
    public ResponseEntity<APIDeResponse> deleteSubjectYearById(@PathVariable long id) {
        try {
            subjectYearService.deleteSubjectYearById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete subject of year with id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(APIDeResponse.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

}
