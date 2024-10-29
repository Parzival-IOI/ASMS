package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoRegisterYear.request.RegisterYearRequest;
import com.java.asms.dtos.dtoRegisterYear.response.RegisterYearResponse;
import com.java.asms.services.RegisterYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/year-register")
public class RegisterYearController {
    private final RegisterYearService registerYearService;
    @PostMapping("/create/year-register")
    public ResponseEntity<APIResponse<Object>> createYearRegister(
            @RequestBody RegisterYearRequest registerYearRequest
            ){
        RegisterYearResponse registerYearResponse = registerYearService.createYearRegister(registerYearRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Create Register year successful .")
                .payload(registerYearResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/register-yearBy/{id}")
    public ResponseEntity<APIResponse<Object>> getRegisterYearById(@PathVariable long id){
        RegisterYearResponse registerYearResponse = registerYearService.getRegisterYearById(id);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get register with id "+id+" successful .")
                .payload(registerYearResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/register-year/{id}")
    public ResponseEntity<APIResponse<Object>> updateRegisterYearById(
            @PathVariable long id,
            @RequestBody RegisterYearRequest registerYearRequest
    ){
        RegisterYearResponse registerYearResponse = registerYearService.updateRegisterYearById(id,registerYearRequest);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Update register year with id "+id+" successful .")
                .payload(registerYearResponse)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/register-yearBy/{id}")
    public ResponseEntity<APIDeResponse> deleteRegisterYearById(@PathVariable long id) {
        try {
            registerYearService.deleteRegisterYearById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete register year with id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (RuntimeException e) {
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Error deleting register year with id " + id + ": " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND) // You can customize the status based on the exception
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiDeResponse);
        }
    }

    @GetMapping("/getAll/register-year")
    public ResponseEntity<APIResponse<Object>> getAllRegisterYear(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<RegisterYearResponse> registerYearResponses = registerYearService.getAllRegisterYear(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<Object> apiResponse = APIResponse.builder()
                .message("Get all successful .")
                .payload(registerYearResponses)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }


}
