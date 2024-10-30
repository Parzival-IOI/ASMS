package com.java.asms.repositories;

import com.java.asms.models.StudentYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentYearRepository extends JpaRepository<StudentYear, Integer> {
}
