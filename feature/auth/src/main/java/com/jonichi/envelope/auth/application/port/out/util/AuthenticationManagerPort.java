package com.jonichi.envelope.auth.application.port.out.util;

/**
 * A port for managing authentication operations.
 *
 * <p>The {@code AuthenticationManagerPort} interface defines the contract for handling
 * the authentication of users based on their username and password.</p>
 */
public interface AuthenticationManagerPort {

    /**
     * Authenticates a user based on their username and password.
     *
     * <p>This method performs the authentication process, typically by verifying the
     * provided credentials against a stored user record.</p>
     *
     * @param username the username of the user to authenticate
     * @param password the password of the user to authenticate
     */
    void authenticate(String username, String password);
}
