package com.jonichi.envelope.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AuthenticationRequest(
        @NotEmpty(message = "Email is required. Please enter a valid value.")
        @Email(message = "Invalid email.")
        String email,
        @NotEmpty(message = "Password is required. Please enter a valid value.")
        String password
) {}
