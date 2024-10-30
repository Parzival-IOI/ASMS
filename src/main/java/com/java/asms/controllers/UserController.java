package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoUser.request.UserRequest;
import com.java.asms.dtos.dtoUser.response.UserResponse;
import com.java.asms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/create/user")
    public ResponseEntity<APIResponse<?>> createUser(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        APIResponse<?> apiResponse = APIResponse.builder()
                .message("create user successful .")
                .payload(userResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/userBy/{id}")
    public ResponseEntity<APIResponse<?>> getUserById(@PathVariable long id) {
        try {
            UserResponse userResponse = userService.getUserById(id);
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Get user with id " + id + " successful.")
                    .payload(userResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> errorResponse = APIResponse.builder()
                    .message("Error retrieving user with id " + id + ": " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/update/userBy/{id}")
    public ResponseEntity<APIResponse<?>> updateUserById(@PathVariable long id, @RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.updateUserById(id, userRequest);
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Update user with id " + id + " successful.")
                    .payload(userResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> errorResponse = APIResponse.builder()
                    .message("Error updating user with id " + id + ": " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/delete/userBy/{id}")
    public ResponseEntity<APIDeResponse> deleteUserById(@PathVariable long id) {
        try {
            userService.deleteUserById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete user with id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (Exception e) {
            APIDeResponse errorResponse = APIDeResponse.builder()
                    .message("Error deleting user with id " + id + ": " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getAll/users")
    public ResponseEntity<APIResponse<?>> getAllUser(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<UserResponse> userResponseList = userService.getAllUser(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<?> apiResponse = APIResponse.builder()
                .message("Get all successful .")
                .payload(userResponseList)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
