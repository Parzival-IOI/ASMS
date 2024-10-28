package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoDepartment.departmentRequest.DepartmentRequest;
import com.java.asms.dtos.dtoDepartment.departmentResponse.DepartmentResponse;
import com.java.asms.models.Department;
import com.java.asms.services.DepartmentService;
import com.java.asms.utils.ResException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DepartmentController {
    private final DepartmentService departmentService;

//    @GetMapping("/findOne")
//    public ResponseEntity<?> findOneDepartment(@RequestBody long id) {
//        try {
//            Department department = departmentService.findOne(id);
//            return new ResponseEntity<>(department, HttpStatus.OK);
//        } catch (ResException ex) {
//            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
//        } catch (Exception ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PutMapping("/update")
//    public ResponseEntity<?> updateDepartment(@RequestBody Department department) {
//        try {
//            departmentService.update(department);
//            return new ResponseEntity<>("success", HttpStatus.OK);
//        } catch (ResException ex) {
//            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
//        } catch (Exception ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<?> deleteDepartment(@RequestBody long id) {
//        try {
//            departmentService.delete(id);
//            return new ResponseEntity<>("success", HttpStatus.OK);
//        } catch (ResException ex) {
//            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
//        } catch (Exception ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PostMapping("/save")
//    public ResponseEntity<?> saveDepartment(@RequestBody DepartmentRequest department) {
//        try {
//            departmentService.insert(department);
//            return new ResponseEntity<>("success", HttpStatus.OK);
//        } catch (ResException ex) {
//            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
//        } catch (Exception ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/create/department")
    public ResponseEntity<APIResponse<Object>> createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        DepartmentResponse departmentResponse = departmentService.createDepartment(departmentRequest);

        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Created department successfully.")
                .payload(departmentResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/departmentBy/{id}")
    public ResponseEntity<APIResponse<Object>> getDepartmentById(@PathVariable long id){
        DepartmentResponse getDepartmentId = departmentService.getDepartmentById(id);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get department ID "+ id +" successful.")
                .payload(getDepartmentId)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/departmentBy/{id}")
    public ResponseEntity<APIResponse<Object>> updateDepartmentById(
            @PathVariable long id,
            @RequestBody DepartmentRequest departmentRequest){
            DepartmentResponse departmentResponse = departmentService.updateDepartmentById(id,departmentRequest);
            APIResponse<Object> apiResponse = APIResponse.builder()
                    .message("Updated department by "+ id +"successful.")
                    .payload(departmentResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/departmentBy/{id}")
    public ResponseEntity<APIDeResponse> deleteDepartmentById(@PathVariable long id){
        departmentService.deleteDepartmentById(id);
        APIDeResponse apiDeResponse = APIDeResponse.builder()
                .message("Deleted department by id "+ id +" successful.")
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.accepted().body(apiDeResponse);
    }


}
