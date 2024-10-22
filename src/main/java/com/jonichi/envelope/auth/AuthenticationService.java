package com.jonichi.envelope.auth;

import com.jonichi.envelope.config.JwtService;
import com.jonichi.envelope.user.Role;
import com.jonichi.envelope.user.User;
import com.jonichi.envelope.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResult register(RegisterRequest request) {
        logger.info("Start - register");

        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        logger.info("End - register");
        return AuthenticationResult.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResult authenticate(AuthenticationRequest request) {
        logger.info("Start - authenticate");
        if (logger.isDebugEnabled()) {
            logger.debug("Request: email={}", request.email());
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = repository.findUserByEmail(request.email())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        logger.info("End - authenticate");
        return AuthenticationResult.builder()
                .token(jwtToken)
                .build();
    }

}
