package com.java.asms.dtos.dtoSubject.subjectRequest;

import com.java.asms.models.Major;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequest {
    private int year;
    private String name;
    private String description;
    private long majorId;
}
