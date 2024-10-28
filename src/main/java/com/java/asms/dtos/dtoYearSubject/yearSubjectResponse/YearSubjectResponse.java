package com.java.asms.dtos.dtoYearSubject.yearSubjectResponse;

import com.java.asms.dtos.dtoSubject.subjectResponse.DTOSubjectYearResponse;
import com.java.asms.dtos.dtoSubject.subjectResponse.SubjectResponse;
import com.java.asms.dtos.dtoYear.responseYear.DTOYearResponse;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class YearSubjectResponse {
    private long id;
    private DTOSubjectYearResponse subject;
    private DTOYearResponse year;
}
