package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoTransactionStudentYear.request.RequestTransaction;
import com.java.asms.dtos.dtoTransactionStudentYear.response.ResponseTransaction;
import com.java.asms.services.TransactionStudentYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction-student-year")
public class TransactionStudentYearController {
    private final TransactionStudentYearService transactionStudentYearService;
    @PostMapping("/create/transaction")
    public ResponseEntity<APIResponse<?>> createTransaction(@RequestBody RequestTransaction requestTransaction){
        ResponseTransaction responseTransaction = transactionStudentYearService.createTransaction(requestTransaction);
        APIResponse<?> apiResponse = APIResponse.builder()
                .message("Create Transaction successful .")
                .payload(responseTransaction)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/transactionBy/{id}")
    public ResponseEntity<APIResponse<?>> updateTransactionById(@PathVariable long id, @RequestBody RequestTransaction requestTransaction) {
        try {
            ResponseTransaction responseTransaction = transactionStudentYearService.updateTransactionById(id, requestTransaction);
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Update transaction student year by id " + id + " successful.")
                    .payload(responseTransaction)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.builder()
                            .message("Failed to update transaction: " + e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @GetMapping("/get/transactionBy/{id}")
    public ResponseEntity<APIResponse<?>> getTransactionById(@PathVariable long id) {
        try {
            ResponseTransaction responseTransaction = transactionStudentYearService.getTransactionById(id);
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Get transaction student year by " + id + " successful.")
                    .payload(responseTransaction)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.builder()
                            .message("Failed to get transaction: " + e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @DeleteMapping("/delete/transactionBy/{id}")
    public ResponseEntity<APIDeResponse> deleteTransactionById(@PathVariable long id) {
        try {
            transactionStudentYearService.deleteTransactionById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete transaction by id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIDeResponse.builder()
                            .message("Failed to delete transaction: " + e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .dateTime(LocalDateTime.now())
                            .build());
        }
    }

    @GetMapping("/getAll/transaction")
    public ResponseEntity<APIResponse<?>> getAllTransaction(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<ResponseTransaction> responseTransactionList = transactionStudentYearService.getAllTransaction(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<?> apiResponse = APIResponse.builder()
                .message("Get all successful .")
                .payload(responseTransactionList)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);

    }

}
