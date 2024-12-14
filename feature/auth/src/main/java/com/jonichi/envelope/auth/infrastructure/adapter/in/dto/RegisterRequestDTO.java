package com.jonichi.envelope.auth.infrastructure.adapter.in.dto;

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
        String username,
        String email,
        String password
) {
}
