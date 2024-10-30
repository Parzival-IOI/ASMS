package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoClassroom.request.ClassroomRequest;
import com.java.asms.dtos.dtoClassroom.response.ClassroomResponse;
import com.java.asms.services.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classroom")
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;
    @PostMapping("/create/class")
    public ResponseEntity<APIResponse<Object>> createClass(@RequestBody ClassroomRequest classroomRequest){
        ClassroomResponse classroomResponse = classroomService.createClass(classroomRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create class successful .")
                .payload(classroomResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/classBy/{id}")
    public ResponseEntity<APIResponse<Object>> getClassById(@PathVariable long id) {
        try {
            ClassroomResponse classroomResponse = classroomService.getClassById(id);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Get class by id " + id + " successful.")
                    .payload(classroomResponse)
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
        } catch (Exception e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("An error occurred while retrieving the class.")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PutMapping("/update/classBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateClassById(@PathVariable long id, @RequestBody ClassroomRequest classroomRequest) {
        try {
            ClassroomResponse classroomResponse = classroomService.updateClassById(id, classroomRequest);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Update class with id " + id + " successful.")
                    .payload(classroomResponse)
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
        } catch (Exception e) {
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("An error occurred while updating the class.")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @DeleteMapping("/delete/classBy/{id}")
    public ResponseEntity<APIDeResponse> deleteClassById(@PathVariable long id) {
        try {
            classroomService.deleteClassById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete class with id " + id + " successful.")
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
                    .message("An error occurred while deleting the class.")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiDeResponse);
        }
    }

    @GetMapping("/getAll/classroom")
    public ResponseEntity<APIResponse<Object>> getAllClassroom(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<ClassroomResponse> classroomResponses = classroomService.getAllClassroom(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get all class successful .")
                .payload(classroomResponses)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }

}
