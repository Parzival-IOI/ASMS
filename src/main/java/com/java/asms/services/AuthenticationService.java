package com.java.asms.services;

import com.java.asms.configs.JwtTimeProperties;
import com.java.asms.dtos.authentication.LoginRequest;
import com.java.asms.dtos.authentication.TokenResponse;
import com.java.asms.models.Login;
import com.java.asms.repositories.LoginRepository;
import com.java.asms.utils.ResException;
import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    private final AuthenticationManager authenticationManager;
    private final LoginRepository loginRepository;
    private final JwtTimeProperties jwtTimeProperties;

    public TokenResponse login(LoginRequest loginRequest) throws Exception {

        log.info(loginRequest.username());
        Optional<Login> login = loginRepository.findByUsername(loginRequest.username());

        if(login.isPresent()) {
            if(!new BCryptPasswordEncoder().matches(loginRequest.password(), login.get().getPassword())) {

                    if(login.get().getAttempt() > 5 || login.get().getIsBlocked()) {
                        return getTokenResponse(loginRequest, login);
                    } else {
                        if(login.get().getAttempt() == 5) {
                            Date bd = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));
                            login.get().setIsBlocked(true);
                            login.get().setBlockedDate(bd);
                        }
                        login.get().setAttempt(login.get().getAttempt() + 1);
                        loginRepository.save(login.get());
                    }

                throw new ResException("Wrong Username / Password", HttpStatus.BAD_REQUEST);
            }
            else {
                if(login.get().getAttempt() > 5 || login.get().getIsBlocked()) {
                    return getTokenResponse(loginRequest, login);
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


            login.get().setRefreshToken(generatedRefreshToken);
            loginRepository.save(login.get());

            return TokenResponse.builder()
                    .accessToken(generatedAccessToken)
                    .refreshToken(generatedRefreshToken)
                    .build();
        }
        throw new ResException("Wrong Username / Password", HttpStatus.BAD_REQUEST);
    }

    private TokenResponse getTokenResponse(LoginRequest loginRequest, Optional<Login> login) throws Exception {
        Date currentDate = new Date();
        if(login.get().getBlockedDate().before(currentDate)) {
            login.get().setAttempt(0);
            login.get().setIsBlocked(false);
            loginRepository.save(login.get());
            return this.login(loginRequest);
        }
        throw new ResException("Attempt limit exceeded", HttpStatus.LOCKED);
    }

    public TokenResponse refreshToken(Principal principal, Jwt jwt) throws Exception {
        Optional<Login> login = loginRepository.findByUsername(principal.getName());
        if(login.isPresent()) {

            if(!login.get().getRefreshToken().equals(jwt.getTokenValue())) {
                throw new ResException("Refresh Token is not valid", HttpStatus.BAD_REQUEST);
            }

            Instant now = Instant.now();
            String role;
            if (login.get().getIsStudent()) {
                role = "STUDENT";
            } else {
                role = login.get().getUser().getRole().getValue();
            }
            //access token
            JwtClaimsSet accessToken = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(jwtTimeProperties.access()* 60L))
                    .subject(login.get().getUsername())
                    .claim("role", "ROLE_" + role)
                    .build();

            //refresh token
            JwtClaimsSet refreshToken = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(jwtTimeProperties.refresh(), ChronoUnit.HOURS))
                    .subject(login.get().getUsername())
                    .claim("role", "ROLE_REFRESH_TOKEN")
                    .claim("token", "refresh")
                    .build();

            String generatedAccessToken = this.jwtEncoder.encode(JwtEncoderParameters.from(accessToken)).getTokenValue();
            String generatedRefreshToken = this.jwtEncoder.encode(JwtEncoderParameters.from(refreshToken)).getTokenValue();

            login.get().setRefreshToken(generatedRefreshToken);
            loginRepository.save(login.get());

            String createdToken = "refresh : " + login.get().getUsername() + "/" + role + "/" + generatedAccessToken;
            log.info(createdToken);

            return TokenResponse.builder()
                    .accessToken(generatedAccessToken)
                    .refreshToken(generatedRefreshToken)
                    .build();
        }
        throw new ResException("User Not Found", HttpStatus.NOT_FOUND);
    }

}
