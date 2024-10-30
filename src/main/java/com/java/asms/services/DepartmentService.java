package com.java.asms.services;

import com.java.asms.dtos.dtoDepartment.departmentRequest.DepartmentRequest;
import com.java.asms.dtos.dtoDepartment.departmentResponse.DepartmentResponse;
import com.java.asms.models.Department;
import com.java.asms.repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        Department department = new Department();
        departmentRequest.requestDepartment(department);

        Department savedDepartment = departmentRepository.save(department);

        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.responseDepartment(savedDepartment);
        return departmentResponse;
    }

    public DepartmentResponse getDepartmentById(long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            return null;
        }
        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.responseDepartment(department);
        return departmentResponse;
    }

    public DepartmentResponse updateDepartmentById(long id, DepartmentRequest departmentRequest) {
        Department existingDepartment = departmentRepository.findById(id).orElse(null);
        departmentRequest.requestDepartment(Objects.requireNonNull(existingDepartment));

        Department updatedDepartment = departmentRepository.save(existingDepartment);

        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.responseDepartment(updatedDepartment);

        return departmentResponse;
    }

    public void deleteDepartmentById(long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    public List<DepartmentResponse> getAllDepartment(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Department> departmentPage = departmentRepository.findAll(pageable);

        List<Department> departmentList = departmentPage.getContent();
        List<DepartmentResponse> departmentResponseList = new ArrayList<>();
        for (Department department: departmentList){
            DepartmentResponse departmentResponse = new DepartmentResponse();
            departmentResponse.responseDepartment(department);
            departmentResponseList.add(departmentResponse);
        }
        return departmentResponseList;
    }
}
