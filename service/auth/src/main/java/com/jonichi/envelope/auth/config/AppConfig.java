package com.jonichi.envelope.auth.config;

import com.jonichi.envelope.auth.application.port.in.AuthUseCase;
import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.application.port.out.UserUtil;
import com.jonichi.envelope.auth.application.port.out.util.AuthenticationManagerPort;
import com.jonichi.envelope.auth.application.port.out.util.JwtUtilPort;
import com.jonichi.envelope.auth.application.port.out.util.PasswordEncoderPort;
import com.jonichi.envelope.auth.application.service.AuthService;
import com.jonichi.envelope.auth.infrastructure.adapter.out.AuthenticationManagerImpl;
import com.jonichi.envelope.auth.infrastructure.adapter.out.PasswordEncoderImpl;
import com.jonichi.envelope.auth.infrastructure.adapter.out.UserJpaRepository;
import com.jonichi.envelope.auth.infrastructure.adapter.out.UserRepository;
import com.jonichi.envelope.auth.infrastructure.adapter.out.UserUtilImpl;
import com.jonichi.envelope.common.util.listener.TransactionalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up authentication-related beans.
 *
 * <p>This configuration class defines the beans necessary for handling user authentication
 * and registration. It wires together services, repositories, and utilities for password encoding,
 * authentication, JWT token generation, and user repository interactions.</p>
 *
 * <p>The beans are initialized using Spring's {@link Bean} annotations, enabling them to be
 * injected into other components of the application.</p>
 */
@Configuration
public class AppConfig {

    private final UserJpaRepository userJpaRepository;
    private final TransactionalHandler transactionalHandler;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an {@link AppConfig} instance with the necessary dependencies injected.
     *
     * @param userJpaRepository the JPA repository for accessing user data
     * @param transactionalHandler the handler for managing transactional operations
     * @param authenticationManager the authentication manager for managing user authentication
     * @param passwordEncoder the password encoder for securing user passwords
     */
    public AppConfig(
            UserJpaRepository userJpaRepository,
            TransactionalHandler transactionalHandler,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userJpaRepository = userJpaRepository;
        this.transactionalHandler = transactionalHandler;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Provides the {@link AuthenticationManagerPort} bean, which wraps the Spring Security
     * {@link AuthenticationManager} to handle authentication logic.
     *
     * @return the {@link AuthenticationManagerPort} implementation
     */
    @Bean
    public AuthenticationManagerPort authenticationManagerPort() {
        return new AuthenticationManagerImpl(authenticationManager);
    }

    /**
     * Provides the {@link UserRepositoryPort} bean, which handles user repository operations.
     *
     * @return the {@link UserRepositoryPort} implementation
     */
    @Bean
    public UserRepositoryPort userRepositoryPort() {
        return new UserRepository(userJpaRepository, transactionalHandler);
    }

    /**
     * Provides the {@link JwtUtilPort} bean, which is responsible for generating and validating
     * JWT tokens.
     *
     * @return the {@link JwtUtilPort} implementation
     */
    @Bean
    public JwtUtilPort jwtUtilPort() {
        return new JwtService();
    }

    /**
     * Provides the {@link PasswordEncoderPort} bean, which handles password encoding and
     * validation.
     *
     * @return the {@link PasswordEncoderPort} implementation
     */
    @Bean
    public PasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderImpl(passwordEncoder);
    }

    /**
     * Provides the {@link AuthUseCase} bean, which is responsible for handling authentication and
     * registration use cases.
     *
     * @return the {@link AuthUseCase} implementation
     */
    @Bean
    public AuthUseCase authUseCase() {
        return new AuthService(
                userRepositoryPort(),
                passwordEncoderPort(),
                jwtUtilPort(),
                authenticationManagerPort()
        );
    }

    /**
     * Creates a new instance of {@link UserUtilImpl} using the provided {@link UserRepository}.
     *
     * @return the {@link UserUtil} implementation for fetching user details
     */
    @Bean
    public UserUtil userUtil() {
        return new UserUtilImpl(
                new UserRepository(userJpaRepository, transactionalHandler)
        );
    }
}
