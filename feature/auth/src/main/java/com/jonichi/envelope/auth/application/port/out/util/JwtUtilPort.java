package com.jonichi.envelope.auth.application.port.out.util;

import com.jonichi.envelope.auth.domain.User;

/**
 * A port for generating JWT tokens.
 *
 * <p>The {@code JwtUtilPort} interface defines the contract for generating
 * JSON Web Tokens (JWT) for a user. This token can then be used for user authentication
 * in a secure manner.</p>
 */
public interface JwtUtilPort {

    /**
     * Generates a JWT token for the given user.
     *
     * <p>This method takes a {@link User} object and generates a corresponding JWT token
     * that can be used for authentication or authorization purposes.</p>
     *
     * @param user the user for whom the JWT token will be generated
     * @return a JWT token representing the authenticated user
     */
    String generateToken(User user);
}
