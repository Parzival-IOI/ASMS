package com.java.asms.repositories;

import com.java.asms.models.RegisterYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterYearRepository extends JpaRepository<RegisterYear,Integer> {
}
