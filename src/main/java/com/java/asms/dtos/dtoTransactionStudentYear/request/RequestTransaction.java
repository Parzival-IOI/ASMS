package com.java.asms.dtos.dtoTransactionStudentYear.request;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestTransaction {
    private String transactionID;
    private String channel;
    private long paid;
    private long studentId;
    private long makerId;
    private long checkerId;
    private long studentYearId;
}
