package com.jonichi.envelope.auth.application.service;

import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.application.port.out.util.JwtUtilPort;
import com.jonichi.envelope.auth.application.port.out.util.PasswordEncoderPort;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.common.exception.EnvelopeDuplicateException;

public class AuthService {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtUtilPort jwtUtilPort;

    public AuthService(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort, JwtUtilPort jwtUtilPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtUtilPort = jwtUtilPort;
    }

    public String register(String username, String email, String password) {
        if (userRepositoryPort.findByUsername(username).isPresent()) {
            throw new EnvelopeDuplicateException("Username already exists");
        }

        String encodedPassword = passwordEncoderPort.encode(password);
        User user = new User(username, email, encodedPassword);
        userRepositoryPort.save(user);

        return jwtUtilPort.generateToken(user);
    }
}
