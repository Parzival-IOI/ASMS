package com.java.asms.dtos.dtoMajor.majorRequest;

import com.java.asms.models.BaseEntity;
import com.java.asms.models.Department;
import com.java.asms.models.Major;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MajorRequest  {
    private String name;
    private String price;
    private int studyYear;
    private String description;
    private long departmentId;

    public void requestMajor(Major major) {
        major.setName(this.name);
        major.setPrice(this.price);
        major.setStudyYear(this.studyYear);
        major.setDescription(this.description);
    }
}
