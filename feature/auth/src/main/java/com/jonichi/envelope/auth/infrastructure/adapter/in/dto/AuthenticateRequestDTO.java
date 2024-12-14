package com.jonichi.envelope.auth.infrastructure.adapter.in.dto;

/**
 * A DTO for user authentication requests.
 *
 * <p>This record encapsulates the username and password submitted by the user during
 * authentication.</p>
 *
 * @param username the username of the user attempting to authenticate
 * @param password the password of the user attempting to authenticate
 */
public record AuthenticateRequestDTO(String username, String password) {
}
