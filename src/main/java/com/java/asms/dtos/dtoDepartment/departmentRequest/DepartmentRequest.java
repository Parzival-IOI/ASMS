package com.java.asms.dtos.dtoDepartment.departmentRequest;

import com.java.asms.models.Department;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {
    private String departmentName;
    public void requestDepartment(Department department){
        department.setName(this.departmentName);
    }
}
