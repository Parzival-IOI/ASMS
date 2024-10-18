package com.java.asms.controllers;

import com.java.asms.enums.UserRole;
import com.java.asms.enums.UserStatus;
import com.java.asms.models.User;
import com.java.asms.repositories.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/migrate")
@RequiredArgsConstructor

public class Migrate {
    private final UserRepository userRepository;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> migrate() {
        Optional<User> user = userRepository.findByUsername("Parzival");

        if(user.isPresent())
            return new ResponseEntity<>("Already Migrated", HttpStatus.BAD_REQUEST);

        User admin = User.builder()
                .firstName("Parzival")
                .lastName("LOL")
                .username("Parzival")
                .status(UserStatus.ENABLE)
                    .password(new BCryptPasswordEncoder().encode("admin"))
                .role(UserRole.ADMIN)
                .build();

        userRepository.save(admin);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/test")
    public String getd() {
        return "suv";
    }
}
