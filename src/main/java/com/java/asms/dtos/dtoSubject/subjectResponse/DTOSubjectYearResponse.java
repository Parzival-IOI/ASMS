package com.java.asms.dtos.dtoSubject.subjectResponse;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DTOSubjectYearResponse {
    private long id;
    private int year;
    private String name;
    private String description;
}
