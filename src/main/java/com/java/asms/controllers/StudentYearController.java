package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoStudentYear.request.StudentYearRequest;
import com.java.asms.dtos.dtoStudentYear.response.StudentYearResponse;
import com.java.asms.services.StudentYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student-year")
public class StudentYearController {
    private final StudentYearService studentYearService;

    @PostMapping("/create/student-year")
    public ResponseEntity<APIResponse<Object>> createStudentYear(@RequestBody StudentYearRequest studentYearRequest){
        StudentYearResponse studentYearResponse = studentYearService.createStudentYear(studentYearRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create student year successful .")
                .payload(studentYearResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/student-yearBy/{id}")
    public ResponseEntity<Object> getStudentYearById(@PathVariable long id) {
        try {
            StudentYearResponse studentYearResponse = studentYearService.getStudentYearById(id);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Get student year with id " + id + " successful.")
                    .payload(studentYearResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    APIResponse.builder()
                            .message("Error: " + ex.getMessage())
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    APIResponse.builder()
                            .message("Unexpected error occurred.")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PutMapping("/update/student-yearBy/{id}")
    public ResponseEntity<Object> updateStudentYearById(@PathVariable long id, @RequestBody StudentYearRequest studentYearRequest) {
        try {
            StudentYearResponse studentYearResponse = studentYearService.updateStudentYearById(id, studentYearRequest);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Update student year with id " + id + " successful.")
                    .payload(studentYearResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    APIResponse.builder()
                            .message("Error: " + ex.getMessage())
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    APIResponse.builder()
                            .message("Unexpected error occurred.")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
        }
    }

    @DeleteMapping("delete/student-yearBy/{id}")
    public ResponseEntity<Object> deleteStudentYearById(@PathVariable long id) {
        try {
            studentYearService.deleteStudentYearById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete student year by id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    APIDeResponse.builder()
                            .message("Error: " + ex.getMessage())
                            .status(HttpStatus.NOT_FOUND)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    APIDeResponse.builder()
                            .message("Unexpected error occurred.")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
        }
    }

    @GetMapping("/getAll/student-year")
    public ResponseEntity<APIResponse<Object>>  getAllStudentYear(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<StudentYearResponse> studentYearResponses = studentYearService.getAllStudentYear(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get all successful .")
                .payload(studentYearResponses)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }


}
