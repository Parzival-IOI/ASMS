package com.java.asms.dtos.dtoSubject.subjectResponse;

import com.java.asms.dtos.dtoMajor.majorResponse.GenerationMajorResponse;
import com.java.asms.dtos.dtoMajor.majorResponse.MajorResponse;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private long id;
    private int year;
    private String name;
    private String description;
    private GenerationMajorResponse major;
}
