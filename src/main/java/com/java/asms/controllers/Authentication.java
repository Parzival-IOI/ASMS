package com.java.asms.controllers;

import com.java.asms.dtos.authentication.LoginRequest;
import com.java.asms.dtos.authentication.TokenResponse;
import com.java.asms.services.AuthenticationService;
import com.java.asms.utils.ResException;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class Authentication {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            TokenResponse tr = authenticationService.login(loginRequest);
            return new ResponseEntity<>(tr, HttpStatus.OK);
        } catch (ResException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refreshToken(Principal principal, @AuthenticationPrincipal Jwt jwt) {
        try {
            TokenResponse tr = authenticationService.refreshToken(principal, jwt);;
            return new ResponseEntity<>(tr, HttpStatus.OK);
        } catch (ResException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
