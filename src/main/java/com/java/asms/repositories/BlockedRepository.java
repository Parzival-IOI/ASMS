package com.java.asms.repositories;

import com.java.asms.models.Blocked;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedRepository extends JpaRepository<Blocked, Long> {
    Optional<Blocked> findByUserId(int id);
}
