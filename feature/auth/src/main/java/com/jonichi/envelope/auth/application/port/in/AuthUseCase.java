package com.jonichi.envelope.auth.application.port.in;

public interface AuthUseCase {
    String register(String username, String email, String password);
}
