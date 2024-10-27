package com.java.asms.repositories;

import com.java.asms.models.Login;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    Optional<Login> findByUserId(long userId);
    Optional<Login> findByUsername(String username);
}
