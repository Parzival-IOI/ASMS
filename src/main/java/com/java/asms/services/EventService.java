package com.java.asms.services;

import com.java.asms.dtos.dtoEvent.request.EventRequest;
import com.java.asms.dtos.dtoEvent.response.EventResponse;
import com.java.asms.dtos.dtoUser.response.UserResponse;
import com.java.asms.models.Event;
import com.java.asms.models.StudentYear;
import com.java.asms.models.User;
import com.java.asms.repositories.EventRepository;
import com.java.asms.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    public EventResponse createEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setCategory(eventRequest.getCategory());

        User maker = userRepository.findById((int) eventRequest.getMakerId())
                .orElseThrow(() -> new RuntimeException("Maker not found with ID: " + eventRequest.getMakerId()));

        User checker = userRepository.findById((int) eventRequest.getCheckerId())
                .orElseThrow(() -> new RuntimeException("Checker not found with ID: " + eventRequest.getCheckerId()));

        event.setMaker(maker);
        event.setChecker(checker);

        Event savedEvent = eventRepository.save(event);

        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(savedEvent.getId());
        eventResponse.setTitle(savedEvent.getTitle());
        eventResponse.setDescription(savedEvent.getDescription());
        eventResponse.setLocation(savedEvent.getLocation());
        eventResponse.setCategory(savedEvent.getCategory());

        if (savedEvent.getMaker() != null) {
            UserResponse makerResponse = new UserResponse();
            makerResponse.setId(savedEvent.getMaker().getId());
            makerResponse.setFirstName(savedEvent.getMaker().getFirstName());
            makerResponse.setLastName(savedEvent.getMaker().getLastName());
            makerResponse.setDob(savedEvent.getMaker().getDob());
            makerResponse.setRole(savedEvent.getMaker().getRole());
            makerResponse.setPhone(savedEvent.getMaker().getPhone());
            makerResponse.setEmail(savedEvent.getMaker().getEmail());
            makerResponse.setNationalId(savedEvent.getMaker().getNationalId());
            makerResponse.setAddress(savedEvent.getMaker().getAddress());

            eventResponse.setMaker(makerResponse);
        }

        if (savedEvent.getChecker() != null) {
            UserResponse checkerResponse = new UserResponse();
            checkerResponse.setId(savedEvent.getChecker().getId());
            checkerResponse.setFirstName(savedEvent.getChecker().getFirstName());
            checkerResponse.setLastName(savedEvent.getChecker().getLastName());
            checkerResponse.setDob(savedEvent.getChecker().getDob());
            checkerResponse.setRole(savedEvent.getChecker().getRole());
            checkerResponse.setPhone(savedEvent.getChecker().getPhone());
            checkerResponse.setEmail(savedEvent.getChecker().getEmail());
            checkerResponse.setNationalId(savedEvent.getChecker().getNationalId());
            checkerResponse.setAddress(savedEvent.getChecker().getAddress());

            eventResponse.setChecker(checkerResponse);
        }

        return eventResponse;
    }


    public EventResponse updateEvent(long id, EventRequest eventRequest) {
        Event event = eventRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Event with ID " + id + " not found."));
        eventRequest.requestEvent(event);
        if (eventRequest.getMakerId() != 0) {
            User maker = userRepository.findById((int) eventRequest.getMakerId())
                    .orElseThrow(() -> new RuntimeException("Maker with ID " + eventRequest.getMakerId() + " not found."));
            event.setMaker(maker);
        }
        if (eventRequest.getCheckerId() != 0) {
            User checker = userRepository.findById((int) eventRequest.getCheckerId())
                    .orElseThrow(() -> new RuntimeException("Checker with ID " + eventRequest.getCheckerId() + " not found."));
            event.setChecker(checker);
        }
        Event updatedEvent = eventRepository.save(event);
        EventResponse eventResponse = new EventResponse();
        eventResponse.responseEvent(updatedEvent);

        return eventResponse;
    }

    public EventResponse getEventById(long id) {
        Event event = eventRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Event with ID " + id + " not found."));

        EventResponse eventResponse = new EventResponse();
        eventResponse.responseEvent(event);

        return eventResponse;
    }

    public void deleteEventById(long id) {
        Event event = eventRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Event with ID " + id + " not found."));
        eventRepository.delete(event);
    }

    public List<EventResponse> getAllEvent(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        List<Event> eventList = eventPage.getContent();
        List<EventResponse> eventResponseList = new ArrayList<>();
        for (Event event: eventList){
            EventResponse eventResponse = new EventResponse();
            eventResponse.responseEvent(event);
            eventResponseList.add(eventResponse);
        }
        return eventResponseList;
    }
}
