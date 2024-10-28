package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoYearSubject.yearSubjectRequest.YearSubjectRequest;
import com.java.asms.dtos.dtoYearSubject.yearSubjectResponse.YearSubjectResponse;
import com.java.asms.services.SubjectYearService;
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
    public ResponseEntity<APIResponse<Object>> getYearSubjectById(@PathVariable long id){
        YearSubjectResponse yearSubjectResponse = subjectYearService.getYearSubjectById(id);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get year of subject by id "+id+ "successful .")
                .payload(yearSubjectResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/subject-yearBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateSubjectYearById(@PathVariable long id , @RequestBody YearSubjectRequest yearSubjectRequest){
        YearSubjectResponse yearSubjectResponse = subjectYearService.updateSubjectYearById(id,yearSubjectRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Update subject of year with id "+id+" successful.")
                .payload(yearSubjectResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
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
