package com.jonichi.envelope.auth.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * A Data Transfer Object (DTO) for capturing user registration information.
 *
 * <p>The {@code RegisterRequestDTO} class holds the necessary data for a new user
 * registration, including the user's username, email, and password.</p>
 *
 * @param username the username chosen by the user for registration
 * @param email the email address of the user
 * @param password the password chosen by the user for registration
 */
public record RegisterRequestDTO(
        @NotEmpty(message = "Username is required")
        String username,

        @NotEmpty(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        @NotEmpty(message = "Password is required")
        String password
) {
}
