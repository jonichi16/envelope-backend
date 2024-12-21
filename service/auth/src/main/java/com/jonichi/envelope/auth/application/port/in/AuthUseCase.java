package com.jonichi.envelope.auth.application.port.in;

import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthTokenDTO;

/**
 * Interface representing the use case for user authentication.
 * It defines the methods related to user registration and authentication.
 *
 * <p>The {@code AuthUseCase} interface serves as a contract for authentication services,
 * specifying the methods for registering users and handling their credentials.</p>
 */
public interface AuthUseCase {

    /**
     * Registers a new user and returns an authentication token.
     *
     * <p>This method handles the registration process by accepting the user's username,
     * email, and password. Upon successful registration, it returns a JWT token for the user.</p>
     *
     * @param username the username of the user to register
     * @param email the email address of the user to register
     * @param password the password for the user to register
     * @return a JWT token representing the authenticated user
     */
    AuthTokenDTO register(String username, String email, String password);

    /**
     * Authenticates an existing user and returns an authentication token.
     *
     * <p>This method accepts the user's username and password, performs the authentication process,
     * and returns a JWT token for the user if the credentials are valid.</p>
     *
     * @param username the username of the user to authenticate
     * @param password the password of the user to authenticate
     * @return a JWT token representing the authenticated user
     */
    String authenticate(String username, String password);
}
