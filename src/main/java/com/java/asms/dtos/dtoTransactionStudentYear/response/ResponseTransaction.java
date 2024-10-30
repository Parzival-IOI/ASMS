package com.java.asms.dtos.dtoTransactionStudentYear.response;

import com.java.asms.dtos.dtoStudentYear.response.StudentYearResponse;
import com.java.asms.dtos.dtoUser.response.UserResponse;
import com.java.asms.models.StudentYear;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseTransaction {
    private long id;
    private String transactionID;
    private String channel;
    private long paid;
    private long studentId;
    private UserResponse maker;
    private UserResponse checker;
    private StudentYearResponse studentYear;

}
