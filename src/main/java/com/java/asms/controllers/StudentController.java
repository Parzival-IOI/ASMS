package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoStudent.requestStudent.StudentRequest;
import com.java.asms.dtos.dtoStudent.responseStudent.StudentResponse;
import com.java.asms.dtos.dtoSubject.subjectResponse.SubjectResponse;
import com.java.asms.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentService studentService;
    @PostMapping("/create/student")
    public ResponseEntity<APIResponse<Object>> createStudent(
            @RequestBody StudentRequest studentRequest
            ){
        StudentResponse studentResponse = studentService.createStudent(studentRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create successful .")
                .payload(studentResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.accepted().body(apiResponse);
    }

    @GetMapping("/get/studentBy/{id}")
    public ResponseEntity<APIResponse<Object>> getStudentById(@PathVariable long id) {
        try {
            StudentResponse studentResponse = studentService.getStudentById(id);
            if (studentResponse == null) {
                APIResponse<Object> apiResponse = APIResponse.builder()
                        .message("Student with id " + id + " not found.")
                        .status(HttpStatus.NOT_FOUND)
                        .dateTime(LocalDateTime.now())
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Get student with id " + id + " successful.")
                    .payload(studentResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("An error occurred while retrieving student with id " + id + ": " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PutMapping("/update/studentBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateStudentById(@PathVariable long id, @RequestBody StudentRequest studentRequest) {
        try {
            StudentResponse studentResponse = studentService.updateStudentById(id, studentRequest);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Update student with id " + id + " successful.")
                    .payload(studentResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Error updating student: " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("An unexpected error occurred: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @DeleteMapping("/delete/studentBy/{id}")
    public ResponseEntity<APIDeResponse> deleteStudentById(@PathVariable long id) {
        try {
            studentService.deleteStudentById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete student with id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (RuntimeException e) {
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Error deleting student: " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiDeResponse);
        } catch (Exception e) {
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("An unexpected error occurred: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiDeResponse);
        }
    }

    @GetMapping("/getAll/student")
    public ResponseEntity<APIResponse<Object>> getAllStudent(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<StudentResponse> studentResponses= studentService.getAllStudent(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get all successful.")
                .payload(studentResponses)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }

}
