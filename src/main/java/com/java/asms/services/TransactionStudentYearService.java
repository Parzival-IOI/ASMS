package com.java.asms.services;

import com.java.asms.dtos.dtoClassroom.response.ClassroomResponse;
import com.java.asms.dtos.dtoStudent.responseStudent.StudentResponse;
import com.java.asms.dtos.dtoStudentYear.response.StudentYearResponse;
import com.java.asms.dtos.dtoTransactionStudentYear.request.RequestTransaction;
import com.java.asms.dtos.dtoTransactionStudentYear.response.ResponseTransaction;
import com.java.asms.dtos.dtoUser.response.UserResponse;
import com.java.asms.dtos.dtoYear.responseYear.YearResponse;
import com.java.asms.models.StudentYear;
import com.java.asms.models.SubjectYear;
import com.java.asms.models.TransactionStudentYear;
import com.java.asms.models.User;
import com.java.asms.repositories.StudentYearRepository;
import com.java.asms.repositories.TransactionStudentYearRepository;
import com.java.asms.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionStudentYearService {
    private final TransactionStudentYearRepository transactionStudentYearRepository;
    private final UserRepository userRepository;
    private final StudentYearRepository studentYearRepository;
    public ResponseTransaction createTransaction(RequestTransaction requestTransaction) {
        User maker = userRepository.findById((int) requestTransaction.getMakerId())
                .orElseThrow(() -> new RuntimeException("Maker not found with ID: " + requestTransaction.getMakerId()));

        User checker = userRepository.findById((int) requestTransaction.getCheckerId())
                .orElseThrow(() -> new RuntimeException("Checker not found with ID: " + requestTransaction.getCheckerId()));

        StudentYear studentYear = studentYearRepository.findById((int) requestTransaction.getStudentYearId())
                .orElseThrow(() -> new RuntimeException("StudentYear not found with ID: " + requestTransaction.getStudentYearId()));

        TransactionStudentYear transaction = new TransactionStudentYear();
        transaction.setTransactionID(requestTransaction.getTransactionID());
        transaction.setChannel(requestTransaction.getChannel());
        transaction.setPaid(requestTransaction.getPaid());
        transaction.setStudentId(requestTransaction.getStudentId());
        transaction.setMaker(maker);
        transaction.setChecker(checker);
        transaction.setStudentYear(studentYear);

        TransactionStudentYear savedTransaction = transactionStudentYearRepository.save(transaction);

        ResponseTransaction responseTransaction = new ResponseTransaction();
        responseTransaction.setId(savedTransaction.getId());
        responseTransaction.setTransactionID(savedTransaction.getTransactionID());
        responseTransaction.setChannel(savedTransaction.getChannel());
        responseTransaction.setPaid(savedTransaction.getPaid());
        responseTransaction.setStudentId(savedTransaction.getStudentId());

        if (savedTransaction.getMaker() != null) {
            responseTransaction.setMaker(createUserResponse(savedTransaction.getMaker()));
        }

        if (savedTransaction.getChecker() != null) {
            responseTransaction.setChecker(createUserResponse(savedTransaction.getChecker()));
        }

        if (studentYear != null) {
            responseTransaction.setStudentYear(createStudentYearResponse(studentYear));
        }

        return responseTransaction;
    }

    private UserResponse createUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDob(user.getDob());
        userResponse.setRole(user.getRole());
        userResponse.setPhone(user.getPhone());
        userResponse.setEmail(user.getEmail());
        userResponse.setNationalId(user.getNationalId());
        userResponse.setAddress(user.getAddress());
        return userResponse;
    }

    private StudentYearResponse createStudentYearResponse(StudentYear studentYear) {
        StudentYearResponse studentYearResponse = new StudentYearResponse();
        studentYearResponse.setId(studentYear.getId());
        studentYearResponse.setIsPaid(studentYear.getIsPaid());
        studentYearResponse.setTypeTransaction(studentYear.getTypeTransaction());
        studentYearResponse.setGPA(studentYear.getGPA());
        studentYearResponse.setSubjectScore(studentYear.getSubjectScore());

        if (studentYear.getClassroom() != null) {
            ClassroomResponse classroomResponse = new ClassroomResponse();
            classroomResponse.responseClassroom(studentYear.getClassroom());
            studentYearResponse.setClassroom(classroomResponse);
        } else {
            studentYearResponse.setClassroom(new ClassroomResponse()); 
        }

        if (studentYear.getStudent() != null) {
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.responseStudent(studentYear.getStudent());
            studentYearResponse.setStudent(studentResponse);
        } else {
            studentYearResponse.setStudent(new StudentResponse()); 
        }

        if (studentYear.getYear() != null) {
            YearResponse yearResponse = new YearResponse();
            yearResponse.responseYear(studentYear.getYear());
            studentYearResponse.setYear(yearResponse);
        } else {
            studentYearResponse.setYear(new YearResponse()); 
        }

        return studentYearResponse;
    }


    public ResponseTransaction getTransactionById(long id) {
        TransactionStudentYear transaction = transactionStudentYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        ResponseTransaction responseTransaction = new ResponseTransaction();
        responseTransaction.setId(transaction.getId());
        responseTransaction.setTransactionID(transaction.getTransactionID());
        responseTransaction.setChannel(transaction.getChannel());
        responseTransaction.setPaid(transaction.getPaid());
        responseTransaction.setStudentId(transaction.getStudentId());

        if (transaction.getMaker() != null) {
            responseTransaction.setMaker(createUserResponse(transaction.getMaker()));
        }

        if (transaction.getChecker() != null) {
            responseTransaction.setChecker(createUserResponse(transaction.getChecker()));
        }

        if (transaction.getStudentYear() != null) {
            responseTransaction.setStudentYear(createStudentYearResponse(transaction.getStudentYear()));
        }

        return responseTransaction;
    }

    public ResponseTransaction updateTransactionById(long id, RequestTransaction requestTransaction) {
        TransactionStudentYear existingTransaction = transactionStudentYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        existingTransaction.setTransactionID(requestTransaction.getTransactionID());
        existingTransaction.setChannel(requestTransaction.getChannel());
        existingTransaction.setPaid(requestTransaction.getPaid());
        existingTransaction.setStudentId(requestTransaction.getStudentId());

        if (requestTransaction.getMakerId() > 0) {
            User maker = userRepository.findById((int) requestTransaction.getMakerId())
                    .orElseThrow(() -> new RuntimeException("Maker not found with ID: " + requestTransaction.getMakerId()));
            existingTransaction.setMaker(maker);
        }

        if (requestTransaction.getCheckerId() > 0) {
            User checker = userRepository.findById((int) requestTransaction.getCheckerId())
                    .orElseThrow(() -> new RuntimeException("Checker not found with ID: " + requestTransaction.getCheckerId()));
            existingTransaction.setChecker(checker);
        }

        if (requestTransaction.getStudentYearId() > 0) {
            StudentYear studentYear = studentYearRepository.findById((int) requestTransaction.getStudentYearId())
                    .orElseThrow(() -> new RuntimeException("StudentYear not found with ID: " + requestTransaction.getStudentYearId()));
            existingTransaction.setStudentYear(studentYear);
        }

        TransactionStudentYear updatedTransaction = transactionStudentYearRepository.save(existingTransaction);

        ResponseTransaction responseTransaction = new ResponseTransaction();
        responseTransaction.setId(updatedTransaction.getId());
        responseTransaction.setTransactionID(updatedTransaction.getTransactionID());
        responseTransaction.setChannel(updatedTransaction.getChannel());
        responseTransaction.setPaid(updatedTransaction.getPaid());
        responseTransaction.setStudentId(updatedTransaction.getStudentId());

        if (updatedTransaction.getMaker() != null) {
            responseTransaction.setMaker(createUserResponse(updatedTransaction.getMaker()));
        }

        if (updatedTransaction.getChecker() != null) {
            responseTransaction.setChecker(createUserResponse(updatedTransaction.getChecker()));
        }

        if (updatedTransaction.getStudentYear() != null) {
            responseTransaction.setStudentYear(createStudentYearResponse(updatedTransaction.getStudentYear()));
        }

        return responseTransaction;
    }

    public void deleteTransactionById(long id) {
        TransactionStudentYear existingTransaction = transactionStudentYearRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
        transactionStudentYearRepository.delete(existingTransaction);
    }

    public List<ResponseTransaction> getAllTransaction(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<TransactionStudentYear> transactionStudentYearPage = transactionStudentYearRepository.findAll(pageable);
        List<TransactionStudentYear> transactionStudentYearList = transactionStudentYearPage.getContent();
        List<ResponseTransaction> responseTransactionList = new ArrayList<>();
        for (TransactionStudentYear transactionStudentYear : transactionStudentYearList) {
            ResponseTransaction responseTransaction = new ResponseTransaction();

            responseTransaction.setId(transactionStudentYear.getId());
            responseTransaction.setTransactionID(transactionStudentYear.getTransactionID());
            responseTransaction.setChannel(transactionStudentYear.getChannel());
            responseTransaction.setPaid(transactionStudentYear.getPaid());
            responseTransaction.setStudentId(transactionStudentYear.getStudentId());

            if (transactionStudentYear.getMaker() != null) {
                UserResponse makerResponse = new UserResponse();
                makerResponse.setId(transactionStudentYear.getMaker().getId());
                makerResponse.setFirstName(transactionStudentYear.getMaker().getFirstName());
                makerResponse.setLastName(transactionStudentYear.getMaker().getLastName());
                makerResponse.setDob(transactionStudentYear.getMaker().getDob());
                makerResponse.setRole(transactionStudentYear.getMaker().getRole());
                makerResponse.setPhone(transactionStudentYear.getMaker().getPhone());
                makerResponse.setEmail(transactionStudentYear.getMaker().getEmail());
                makerResponse.setNationalId(transactionStudentYear.getMaker().getNationalId());
                makerResponse.setAddress(transactionStudentYear.getMaker().getAddress());
                responseTransaction.setMaker(makerResponse);
            }

            if (transactionStudentYear.getChecker() != null) {
                UserResponse checkerResponse = new UserResponse();
                checkerResponse.setId(transactionStudentYear.getChecker().getId());
                checkerResponse.setFirstName(transactionStudentYear.getChecker().getFirstName());
                checkerResponse.setLastName(transactionStudentYear.getChecker().getLastName());
                checkerResponse.setDob(transactionStudentYear.getChecker().getDob());
                checkerResponse.setRole(transactionStudentYear.getChecker().getRole());
                checkerResponse.setPhone(transactionStudentYear.getChecker().getPhone());
                checkerResponse.setEmail(transactionStudentYear.getChecker().getEmail());
                checkerResponse.setNationalId(transactionStudentYear.getChecker().getNationalId());
                checkerResponse.setAddress(transactionStudentYear.getChecker().getAddress());
                responseTransaction.setChecker(checkerResponse);
            }

            if (transactionStudentYear.getStudentYear() != null) {
                StudentYearResponse studentYearResponse = new StudentYearResponse();
                studentYearResponse.responseStudentYear(transactionStudentYear.getStudentYear());
                responseTransaction.setStudentYear(studentYearResponse);
            }

            responseTransactionList.add(responseTransaction);
        }

        return responseTransactionList;
    }

}
