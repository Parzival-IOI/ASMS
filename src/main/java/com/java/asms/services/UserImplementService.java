package com.java.asms.services;

import com.java.asms.models.Login;
import com.java.asms.repositories.LoginRepository;
import com.java.asms.repositories.UserRepository;
import com.java.asms.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImplementService implements UserDetailsService {
    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> logins = loginRepository.findByUsername(username);
        String role;
        if (logins.isPresent()) {
            if (logins.get().getIsStudent()) {
                role = "STUDENT";
            } else {
                role = logins.get().getUser().getRole().getValue();
            }
        } else {
            return null;
        }

        return logins.map(login -> User.builder()
                .username(login.getUsername())
                .password(login.getPassword())
                .roles(role)
                .build()).orElse(null);
    }
}
