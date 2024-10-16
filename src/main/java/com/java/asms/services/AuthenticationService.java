package com.java.asms.services;

import com.java.asms.configs.JwtTimeProperties;
import com.java.asms.dtos.authentication.LoginRequest;
import com.java.asms.dtos.authentication.TokenResponse;
import com.java.asms.models.Blocked;
import com.java.asms.models.Login;
import com.java.asms.models.User;
import com.java.asms.repositories.BlockedRepository;
import com.java.asms.repositories.LoginRepository;
import com.java.asms.repositories.UserRepository;
import com.java.asms.utils.ResException;
import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BlockedRepository blockedRepository;
    private final LoginRepository loginRepository;
    private final JwtTimeProperties jwtTimeProperties;

    public TokenResponse login(LoginRequest loginRequest) throws Exception {

        log.info(loginRequest.username());
        Optional<User> user = userRepository.findByUsername(loginRequest.username());

        if(user.isPresent()) {
            if(!new BCryptPasswordEncoder().matches(loginRequest.password(), user.get().getPassword())) {
                Optional<Blocked> blockedUserModel = blockedRepository.findByUserId(user.get().getId());
                if(blockedUserModel.isPresent()) {
                    int attempts = blockedUserModel.get().getAttempt();
                    if(attempts > 5) {
                        throw new ResException("Attempt limit exceeded", HttpStatus.LOCKED);
                    } else {
                        blockedUserModel.get().setAttempt(attempts + 1);
                        blockedRepository.save(blockedUserModel.get());
                    }
                } else {
                    blockedRepository.save(
                            Blocked.builder()
                                    .userId(user.get().getId())
                                    .attempt(1)
                                    .build()
                    );
                }
                throw new ResException("Wrong Username / Password", HttpStatus.BAD_REQUEST);
            }
            else {
                Optional<Blocked> blockedUserModel = blockedRepository.findByUserId(user.get().getId());
                if(blockedUserModel.isPresent()) {
                    blockedUserModel.get().setAttempt(0);
                    blockedRepository.save(blockedUserModel.get());
                }
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
            Instant now = Instant.now();
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            //access token
            JwtClaimsSet accessToken = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(jwtTimeProperties.access()* 60L))
                    .subject(authentication.getName())
                    .claim("role", role)
                    .build();

            //refresh token
            JwtClaimsSet refreshToken = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(jwtTimeProperties.refresh(), ChronoUnit.HOURS))
                    .subject(authentication.getName())
                    .claim("role", "ROLE_REFRESH_TOKEN")
                    .claim("token", "refresh")
                    .build();

            String generatedAccessToken = this.jwtEncoder.encode(JwtEncoderParameters.from(accessToken)).getTokenValue();
            String generatedRefreshToken = this.jwtEncoder.encode(JwtEncoderParameters.from(refreshToken)).getTokenValue();

            String createdToken = "Login : " + authentication.getName() + "/" + role + "/" + generatedAccessToken;
            log.info(createdToken);

            Optional<Login> login = loginRepository.findByUserId(user.get().getId());
            if(login.isPresent()) {
                login.get().setRefreshToken(generatedRefreshToken);
                loginRepository.save(login.get());
            } else {
                loginRepository.save(
                        Login.builder()
                                .userId(user.get().getId())
                                .refreshToken(generatedRefreshToken)
                                .build()
                );
            }

            return TokenResponse.builder()
                    .accessToken(generatedAccessToken)
                    .refreshToken(generatedRefreshToken)
                    .build();
        }
        throw new ResException("Wrong Username / Password", HttpStatus.BAD_REQUEST);
    }

    public TokenResponse refreshToken(Principal principal, Jwt jwt) throws Exception {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if(user.isPresent()) {
            Optional<Login> login = loginRepository.findByUserId(user.get().getId());
            if(login.isPresent()) {
                if(!login.get().getRefreshToken().equals(jwt.getTokenValue())) {
                    throw new ResException("Refresh Token is not valid", HttpStatus.BAD_REQUEST);
                }
            }
            else {
                throw new ResException("Invalid Refresh Token", HttpStatus.BAD_REQUEST);
            }

            Instant now = Instant.now();
            String role = user.get().getRole().getValue();
            //access token
            JwtClaimsSet accessToken = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(jwtTimeProperties.access()* 60L))
                    .subject(user.get().getUsername())
                    .claim("role", "ROLE_" + role)
                    .build();

            //refresh token
            JwtClaimsSet refreshToken = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(jwtTimeProperties.refresh(), ChronoUnit.HOURS))
                    .subject(user.get().getUsername())
                    .claim("role", "ROLE_REFRESH_TOKEN")
                    .claim("token", "refresh")
                    .build();

            String generatedAccessToken = this.jwtEncoder.encode(JwtEncoderParameters.from(accessToken)).getTokenValue();
            String generatedRefreshToken = this.jwtEncoder.encode(JwtEncoderParameters.from(refreshToken)).getTokenValue();

            login.get().setRefreshToken(generatedRefreshToken);
            loginRepository.save(login.get());

            String createdToken = "refresh : " + user.get().getUsername() + "/" + role + "/" + generatedAccessToken;
            log.info(createdToken);

            return TokenResponse.builder()
                    .accessToken(generatedAccessToken)
                    .refreshToken(generatedRefreshToken)
                    .build();
        }
        throw new ResException("User Not Found", HttpStatus.NOT_FOUND);
    }

}
