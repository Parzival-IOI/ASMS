package com.java.asms.repositories;

import com.java.asms.models.SubjectYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectYearRepository extends JpaRepository<SubjectYear, Integer> {
}
