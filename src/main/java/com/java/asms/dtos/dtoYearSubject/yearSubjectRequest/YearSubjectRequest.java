package com.java.asms.dtos.dtoYearSubject.yearSubjectRequest;

import com.java.asms.models.SubjectYear;
import com.java.asms.models.Year;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class YearSubjectRequest {
    private long subjectId;
    private long yearId;
}
