package com.java.asms.dtos.apiResponse;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse <T>{
    private String message;
    private T payload;
    private HttpStatus status;
    private LocalDateTime dateTime;
}
