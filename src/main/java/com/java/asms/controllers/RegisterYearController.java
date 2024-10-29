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
import java.util.NoSuchElementException;

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
    public ResponseEntity<APIResponse<Object>> getRegisterYearById(@PathVariable long id) {
        APIResponse<Object> apiResponse;
        try {
            RegisterYearResponse registerYearResponse = registerYearService.getRegisterYearById(id);
            if (registerYearResponse == null) {
                apiResponse = APIResponse.builder()
                        .message("Register year with id " + id + " not found.")
                        .status(HttpStatus.NOT_FOUND)
                        .dateTime(LocalDateTime.now())
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            apiResponse = APIResponse.builder()
                    .message("Get register with id " + id + " successful.")
                    .payload(registerYearResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);

        } catch (NoSuchElementException e) {
            apiResponse = APIResponse.builder()
                    .message("Register year with id " + id + " not found.")
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (RuntimeException e) {
            apiResponse = APIResponse.builder()
                    .message("An error occurred while fetching register year: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }


    @PutMapping("/update/register-year/{id}")
    public ResponseEntity<APIResponse<Object>> updateRegisterYearById(
            @PathVariable long id,
            @RequestBody RegisterYearRequest registerYearRequest
    ) {
        APIResponse<Object> apiResponse;
        try {
            RegisterYearResponse registerYearResponse = registerYearService.updateRegisterYearById(id, registerYearRequest);
            apiResponse = APIResponse.builder()
                    .message("Update register year with id " + id + " successful.")
                    .payload(registerYearResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            apiResponse = APIResponse.builder()
                    .message("Register year with id " + id + " not found.")
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (RuntimeException e) {
            apiResponse = APIResponse.builder()
                    .message("An error occurred while updating register year: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
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
