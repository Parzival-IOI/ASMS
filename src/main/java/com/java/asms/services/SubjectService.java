package com.java.asms.services;

import com.java.asms.dtos.dtoMajor.majorResponse.GenerationMajorResponse;
import com.java.asms.dtos.dtoSubject.subjectRequest.SubjectRequest;
import com.java.asms.dtos.dtoSubject.subjectResponse.SubjectResponse;
import com.java.asms.models.Major;
import com.java.asms.models.Subject;
import com.java.asms.repositories.MajorRepository;
import com.java.asms.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final MajorRepository majorRepository;

    public SubjectResponse createSubject(SubjectRequest subjectRequest) {
//        set
        Subject subject = new Subject();
        subject.setYear(subjectRequest.getYear());
        subject.setName(subjectRequest.getName());
        subject.setDescription(subjectRequest.getDescription());
        Major major = majorRepository.findById((int) subjectRequest.getMajorId())
                .orElseThrow(() -> new RuntimeException("Major with ID " + subjectRequest.getMajorId() + " not found"));
        subject.setMajor(major);
        Subject savedSubject = subjectRepository.save(subject);
//        Response
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(savedSubject.getId());
        subjectResponse.setYear(savedSubject.getYear());
        subjectResponse.setName(savedSubject.getName());
        subjectResponse.setDescription(savedSubject.getDescription());
//response major
        GenerationMajorResponse majorResponse = new GenerationMajorResponse();
        majorResponse.setId(major.getId());
        majorResponse.setName(major.getName());
        majorResponse.setPrice(major.getPrice());
        majorResponse.setStudyYear(major.getStudyYear());
        majorResponse.setDescription(major.getDescription());
        majorResponse.setCreatedAt(major.getCreatedAt());
        majorResponse.setUpdatedAt(major.getUpdatedAt());
        subjectResponse.setMajor(majorResponse);
        return subjectResponse;
    }

    public SubjectResponse getSubjectById(long id) {
        Subject subject = subjectRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Subject with ID " + id + " not found"));

        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId());
        subjectResponse.setYear(subject.getYear());
        subjectResponse.setName(subject.getName());
        subjectResponse.setDescription(subject.getDescription());

        Major major = subject.getMajor();
        GenerationMajorResponse majorResponse = new GenerationMajorResponse();
        majorResponse.setId(major.getId());
        majorResponse.setName(major.getName());
        majorResponse.setPrice(major.getPrice());
        majorResponse.setStudyYear(major.getStudyYear());
        majorResponse.setDescription(major.getDescription());
        majorResponse.setCreatedAt(major.getCreatedAt());
        majorResponse.setUpdatedAt(major.getUpdatedAt());

        subjectResponse.setMajor(majorResponse);

        return subjectResponse;
    }

    public SubjectResponse updateSubjectById(long id, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Subject with ID " + id + " not found"));

        subject.setYear(subjectRequest.getYear());
        subject.setName(subjectRequest.getName());
        subject.setDescription(subjectRequest.getDescription());

        Major major = majorRepository.findById((int) subjectRequest.getMajorId())
                .orElseThrow(() -> new RuntimeException("Major with ID " + subjectRequest.getMajorId() + " not found"));
        subject.setMajor(major);

        Subject updatedSubject = subjectRepository.save(subject);

        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(updatedSubject.getId());
        subjectResponse.setYear(updatedSubject.getYear());
        subjectResponse.setName(updatedSubject.getName());
        subjectResponse.setDescription(updatedSubject.getDescription());

        GenerationMajorResponse majorResponse = new GenerationMajorResponse();
        majorResponse.setId(major.getId());
        majorResponse.setName(major.getName());
        majorResponse.setPrice(major.getPrice());
        majorResponse.setStudyYear(major.getStudyYear());
        majorResponse.setDescription(major.getDescription());
        majorResponse.setCreatedAt(major.getCreatedAt());
        majorResponse.setUpdatedAt(major.getUpdatedAt());

        subjectResponse.setMajor(majorResponse);

        return subjectResponse;
    }

    public void deleteSubjectById(long id) {
        if (!subjectRepository.existsById((int) id)) {
            throw new RuntimeException("Subject with ID " + id + " not found");
        }
        subjectRepository.deleteById((int) id);
    }

}
