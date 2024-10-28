package com.java.asms.repositories;

import com.java.asms.models.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationRepository extends JpaRepository<Generation ,Integer> {
}