package com.java.asms.dtos.dtoMajor.majorResponse;

import com.java.asms.dtos.dtoDepartment.departmentResponse.DepartmentResponse;
import com.java.asms.models.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenerationMajorResponse {
    private long id;
    private String name;
    private String price;
    private int studyYear;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    public void responseMajor(Major major) {
        this.id = major.getId();
        this.name = major.getName();
        this.price = major.getPrice();
        this.studyYear = major.getStudyYear();
        this.description = major.getDescription();
        this.createdAt = major.getCreatedAt();
        this.updatedAt = major.getUpdatedAt();

    }
}
