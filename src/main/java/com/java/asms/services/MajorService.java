package com.java.asms.services;

import com.java.asms.dtos.dtoMajor.majorRequest.MajorRequest;
import com.java.asms.dtos.dtoMajor.majorResponse.MajorResponse;
import com.java.asms.models.Department;
import com.java.asms.models.Major;
import com.java.asms.repositories.DepartmentRepository;
import com.java.asms.repositories.MajorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MajorService {
    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;

    public MajorResponse createMajor(MajorRequest majorRequest) {
        Major major = new Major();

        Department department = departmentRepository.findById(majorRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        major.setDepartment(department);
        majorRequest.requestMajor(major);
        Major savedMajor = majorRepository.save(major);

        MajorResponse majorResponse = new MajorResponse();
        majorResponse.responseMajor(savedMajor);

        return majorResponse;
    }

    public MajorResponse getMajorById(long id) {
        Major major = majorRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Major not found with id: " + id));
        MajorResponse majorResponse = new MajorResponse();
        majorResponse.responseMajor(major);
        return majorResponse;
    }

    public MajorResponse updateMajorById(long id, MajorRequest majorRequest) {
        Major major = majorRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Major not found with id: " + id));

        majorRequest.requestMajor(major);

        if (!Objects.isNull(majorRequest.getDepartmentId())) {
            Department department = departmentRepository.findById(majorRequest.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            major.setDepartment(department);
        }

        Major updatedMajor = majorRepository.save(major);

        MajorResponse majorResponse = new MajorResponse();
        majorResponse.responseMajor(updatedMajor);

        return majorResponse;
    }

    public void deleteMajorById(long id) {
        Major major = majorRepository.findById((int)id)
                .orElseThrow(() -> new RuntimeException("Major not found with id: " + id));
        majorRepository.delete(major);
    }

    public List<MajorResponse> getAllMajor(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Major> majorPage = majorRepository.findAll(pageable);
        List<Major> majorList = majorPage.getContent();
        List<MajorResponse> majorResponseList = new ArrayList<>();
        for (Major major: majorList){
            MajorResponse majorResponse= new MajorResponse();
            majorResponse.responseMajor(major);
            majorResponseList.add(majorResponse);
        }
        return majorResponseList;
    }
}
