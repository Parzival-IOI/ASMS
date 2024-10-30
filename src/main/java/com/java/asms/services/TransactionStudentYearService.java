package com.java.asms.services;

import com.java.asms.repositories.TransactionStudentYearRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionStudentYearService {
    private final TransactionStudentYearRepository transactionStudentYearRepository;
}
