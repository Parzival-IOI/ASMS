package com.java.asms.controllers;

import com.java.asms.models.Department;
import com.java.asms.services.DepartmentService;
import com.java.asms.utils.ResException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/department/")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("findOne")
    public ResponseEntity<?> findOneDepartment(@RequestBody long id) {
        try {
            Department department = departmentService.findOne(id);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (ResException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateDepartment(@RequestBody Department department) {
        try {
            departmentService.update(department);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (ResException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteDepartment(@RequestBody long id) {
        try {
            departmentService.delete(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (ResException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("save")
    public ResponseEntity<?> saveDepartment(@RequestBody Department department) {
        try {
            departmentService.insert(department);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (ResException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
