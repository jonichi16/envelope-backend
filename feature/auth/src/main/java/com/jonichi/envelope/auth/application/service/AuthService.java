package com.jonichi.envelope.auth.application.service;

import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.application.port.out.util.PasswordEncoderPort;
import com.jonichi.envelope.auth.domain.User;

public class AuthService {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public AuthService(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    public String register(String username, String email, String password) {

        String encodedPassword = passwordEncoderPort.encode(password);
        userRepositoryPort.save(new User(username, email, encodedPassword));

        return "token";
    }
}
