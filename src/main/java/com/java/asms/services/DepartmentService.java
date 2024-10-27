package com.java.asms.services;

import com.java.asms.models.Department;
import com.java.asms.repositories.DepartmentRepository;
import com.java.asms.utils.ResException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department findOne (long id) throws Exception {
        Optional<Department> department = departmentRepository.findById(id);

        if(department.isEmpty()) {
            throw new ResException("Department not found", HttpStatus.NOT_FOUND);
        }

        return department.get();
    }

    public void update(Department department) throws Exception {
        departmentRepository.save(department);
    }

    public void delete(long id) throws Exception {
        departmentRepository.deleteById(id);
    }

    public void insert(Department department) throws Exception {
        departmentRepository.save(department);
    }

}
