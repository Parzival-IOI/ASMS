package com.java.asms.dtos.dtoMajor.majorResponse;

import com.java.asms.dtos.dtoDepartment.departmentResponse.DepartmentResponse;
import com.java.asms.models.Major;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MajorResponse {
    private long id;
    private String name;
    private String price;
    private int studyYear;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private DepartmentResponse department;
    public void responseMajor(Major major) {
        this.id = major.getId();
        this.name = major.getName();
        this.price = major.getPrice();
        this.studyYear = major.getStudyYear();
        this.description = major.getDescription();
        this.createdAt = major.getCreatedAt();
        this.updatedAt = major.getUpdatedAt();
        if (major.getDepartment() != null) {
            this.department = new DepartmentResponse();
            this.department.responseDepartment(major.getDepartment());
        }
    }
}
