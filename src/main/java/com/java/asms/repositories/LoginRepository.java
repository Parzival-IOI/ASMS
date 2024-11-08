package com.java.asms.repositories;

import com.java.asms.models.Login;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByUsername(String username);

    @Modifying
    @Query(value = "update logins u set u.username = ?2, u.password = ?3 where u.id = ?1", nativeQuery = true)
    void updateById(Long id, String username, String password);

    @Modifying
    @Query(value = "update logins u set u.username = ?2 where u.id = ?1", nativeQuery = true)
    void updateUsernameById(Long id, String username);
}
