package com.jonichi.envelope.auth.application.service;

import com.jonichi.envelope.auth.application.port.in.AuthUseCase;
import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.application.port.out.util.AuthenticationManagerPort;
import com.jonichi.envelope.auth.application.port.out.util.JwtUtilPort;
import com.jonichi.envelope.auth.application.port.out.util.PasswordEncoderPort;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthTokenDTO;
import com.jonichi.envelope.common.exception.EnvelopeDuplicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class that implements the user authentication and registration use cases.
 *
 * <p>The {@code AuthService} class provides the logic for user registration and authentication.
 * It interacts with the necessary ports for saving users, encoding passwords, authenticating
 * users, and generating JWT tokens for authenticated users.</p>
 */
public class AuthService implements AuthUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtUtilPort jwtUtilPort;
    private final AuthenticationManagerPort authenticationManagerPort;

    /**
     * Constructs an instance of {@code AuthService}.
     *
     * @param userRepositoryPort         the port to interact with the user repository
     * @param passwordEncoderPort        the port to encode passwords
     * @param jwtUtilPort                the port to generate JWT tokens
     * @param authenticationManagerPort  the port to authenticate users
     */
    public AuthService(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            JwtUtilPort jwtUtilPort,
            AuthenticationManagerPort authenticationManagerPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtUtilPort = jwtUtilPort;
        this.authenticationManagerPort = authenticationManagerPort;
    }

    @Override
    public AuthTokenDTO register(String username, String email, String password) {
        logger.info("Start - Service - register");

        if (userRepositoryPort.findByUsername(username).isPresent()) {
            throw new EnvelopeDuplicateException("Username already exists");
        }

        String encodedPassword = passwordEncoderPort.encode(password);
        User user = userRepositoryPort.save(new User(username, email, encodedPassword));

        logger.info("End - Service - register");
        return new AuthTokenDTO(user.getId(), jwtUtilPort.generateToken(user));
    }

    @Override
    public AuthTokenDTO authenticate(String username, String password) {
        logger.info("Start - Service - authenticate");

        authenticationManagerPort.authenticate(username, password);

        User user = userRepositoryPort.findByUsername(username).orElseThrow();

        logger.info("End - Service - authenticate");
        return new AuthTokenDTO(user.getId(), jwtUtilPort.generateToken(user));
    }
}
