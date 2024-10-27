package com.java.asms.dtos.dtoDepartment.departmentResponse;

import com.java.asms.models.BaseEntity;
import com.java.asms.models.Department;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DepartmentResponse {
    private long id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    public void responseDepartment(Department department){
        id = department.getId();
        name = department.getName();
        this.createdAt = department.getCreatedAt();
        this.updatedAt = department.getUpdatedAt();
    }
}
