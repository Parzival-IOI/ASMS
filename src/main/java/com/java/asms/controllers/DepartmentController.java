package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoDepartment.departmentRequest.DepartmentRequest;
import com.java.asms.dtos.dtoDepartment.departmentResponse.DepartmentResponse;
import com.java.asms.models.Department;
import com.java.asms.services.DepartmentService;
import com.java.asms.utils.ResException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DepartmentController {
    private final DepartmentService departmentService;

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

    @GetMapping("/getAll/department")
    public ResponseEntity<APIResponse<Object>> getAllDepartment(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){

        List<DepartmentResponse> departmentResponseList =
                departmentService.getAllDepartment(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get all customer successful .")
                .payload(departmentResponseList)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }

}
