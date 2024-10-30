package com.java.asms.controllers;

import com.java.asms.dtos.apiResponse.APIDeResponse;
import com.java.asms.dtos.apiResponse.APIResponse;
import com.java.asms.dtos.dtoEvent.request.EventRequest;
import com.java.asms.dtos.dtoEvent.response.EventResponse;
import com.java.asms.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;
    @PostMapping("/create/event")
    public ResponseEntity<APIResponse<?>> createEvent(@RequestBody EventRequest eventRequest){
        EventResponse eventResponse = eventService.createEvent(eventRequest);
        APIResponse<?> apiResponse = APIResponse.builder()
                .message("Create event successful .")
                .payload(eventResponse)
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/update/eventBy/{id}")
    public ResponseEntity<APIResponse<?>> updateEventById(@PathVariable long id, @RequestBody EventRequest eventRequest) {
        try {
            EventResponse eventResponse = eventService.updateEvent(id, eventRequest);
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Update event by Id " + id + " successful.")
                    .payload(eventResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Error updating event: " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("An unexpected error occurred: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @GetMapping("/get/eventBy/{id}")
    public ResponseEntity<APIResponse<?>> getEventById(@PathVariable long id) {
        try {
            EventResponse eventResponse = eventService.getEventById(id);
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Get event by id " + id + " successful.")
                    .payload(eventResponse)
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("Error fetching event: " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = APIResponse.builder()
                    .message("An unexpected error occurred: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @DeleteMapping("/delete/eventBy/{id}")
    public ResponseEntity<APIDeResponse> deleteEventById(@PathVariable long id) {
        try {
            eventService.deleteEventById(id);
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Delete event by id " + id + " successful.")
                    .status(HttpStatus.OK)
                    .dateTime(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(apiDeResponse);
        } catch (RuntimeException e) {
            APIDeResponse apiDeResponse = APIDeResponse.builder()
                    .message("Error deleting event: " + e.getMessage())
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


    @GetMapping("/getAll/event")
    public ResponseEntity<APIResponse<?>> getAllEvent(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        List<EventResponse> eventResponseList = eventService.getAllEvent(pageNo,pageSize,sortBy,sortDirection);
        APIResponse<?> apiResponse = APIResponse.builder()
                .message("Get all successful.")
                .payload(eventResponseList)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
