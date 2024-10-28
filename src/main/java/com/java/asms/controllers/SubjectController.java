package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoSubject.subjectRequest.SubjectRequest;
import com.java.asms.dtos.dtoSubject.subjectResponse.SubjectResponse;
import com.java.asms.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/create/subject")
    public ResponseEntity<APIResponse<Object>> createSubject(@RequestBody SubjectRequest subjectRequest){
        SubjectResponse subjectResponse = subjectService.createSubject(subjectRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create a subject by successful .")
                .payload(subjectResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/subjectBy/{id}")
    public ResponseEntity<APIResponse<Object>> getSubjectById(@PathVariable long id) {
        try {
            SubjectResponse subjectResponse = subjectService.getSubjectById(id);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Get subject with id " + id + " successful.")
                    .payload(subjectResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @PutMapping("/update/subjectBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateSubjectById(
            @PathVariable long id, @RequestBody SubjectRequest subjectRequest) {
        try {
            SubjectResponse subjectResponse = subjectService.updateSubjectById(id, subjectRequest);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Update subject with id " + id + " successful.")
                    .payload(subjectResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }


    @DeleteMapping("/delete/subjectBy/{id}")
    public ResponseEntity<APIDeResponse> deleteSubjectById(@PathVariable long id) {
        try {
            subjectService.deleteSubjectById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete subject with id " + id + " successful.")
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
        } catch (Exception e) {
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("An error occurred while deleting the subject.")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiDeResponse);
        }
    }


}
