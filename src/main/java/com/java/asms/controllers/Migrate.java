package com.java.asms.controllers;

import com.java.asms.enums.LoginStatus;
import com.java.asms.enums.UserRole;
import com.java.asms.models.Login;
import com.java.asms.models.User;
import com.java.asms.repositories.LoginRepository;
import com.java.asms.repositories.UserRepository;
import java.util.Date;
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
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> migrate() {
        Optional<Login> login = loginRepository.findByUsername("Vichhai");

        if(login.isPresent())
            return new ResponseEntity<>("Already Migrated", HttpStatus.BAD_REQUEST);

        User admin = User.builder()
                .firstName("test")
                .lastName("1")
                .role(UserRole.ADMIN)
                .dob(new Date())
                .phone("000000000")
                .nationalId("00000000000")
                .address("HO")
                .build();
        userRepository.save(admin);

        Login adminLogin = Login.builder()
                .user(admin)
                .username("string")
                .password(new BCryptPasswordEncoder().encode("string"))
                .status(LoginStatus.ENABLE)
                .isBlocked(false)
                .isStudent(false)
                .attempt(0)
                .build();
        loginRepository.save(adminLogin);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/test")
    public String getd() {
        return "suv";
    }
}
