package com.java.asms.services;

import com.java.asms.dtos.dtoClassroom.request.ClassroomRequest;
import com.java.asms.dtos.dtoClassroom.response.ClassroomResponse;
import com.java.asms.models.Classroom;
import com.java.asms.repositories.ClassroomRepository;
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
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomResponse createClass(ClassroomRequest classroomRequest) {
        Classroom classroom = new Classroom();
        classroomRequest.requestClassroom(classroom);
        Classroom saveClassroom = classroomRepository.save(classroom);
        ClassroomResponse classroomResponse = new ClassroomResponse();
        classroomResponse.responseClassroom(saveClassroom);
        return classroomResponse;
    }

    public ClassroomResponse getClassById(long id) {
        Classroom classroom = classroomRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Classroom with ID " + id + " not found."));
        ClassroomResponse classroomResponse = new ClassroomResponse();
        classroomResponse.responseClassroom(classroom);
        return classroomResponse;
    }

    public ClassroomResponse updateClassById(long id, ClassroomRequest classroomRequest) {
        Classroom classroom = classroomRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Classroom with ID " + id + " not found."));
        classroomRequest.requestClassroom(classroom);
        Classroom savedClassroom = classroomRepository.save(classroom);
        ClassroomResponse classroomResponse = new ClassroomResponse();
        classroomResponse.responseClassroom(savedClassroom);
        return classroomResponse;
    }

    public void deleteClassById(long id) {
        Classroom classroom = classroomRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Classroom with ID " + id + " not found."));
        classroomRepository.delete(classroom);
    }

    public List<ClassroomResponse> getAllClassroom(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Classroom> classroomPage = classroomRepository.findAll(pageable);
        List<Classroom> classroomList = classroomPage.getContent();
        List<ClassroomResponse> classroomResponseList = new ArrayList<>();
        for (Classroom classroom: classroomList){
            ClassroomResponse classroomResponse = new ClassroomResponse();
            classroomResponse.responseClassroom(classroom);
            classroomResponseList.add(classroomResponse);
        }
        return classroomResponseList;
    }
}
