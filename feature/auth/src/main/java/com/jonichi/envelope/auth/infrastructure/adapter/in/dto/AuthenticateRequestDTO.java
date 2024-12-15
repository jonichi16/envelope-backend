package com.jonichi.envelope.auth.infrastructure.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;

/**
 * A DTO for user authentication requests.
 *
 * <p>This record encapsulates the username and password submitted by the user during
 * authentication.</p>
 *
 * @param username the username of the user attempting to authenticate
 * @param password the password of the user attempting to authenticate
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthenticateRequestDTO(

        @NotEmpty(message = "Username is required")
        String username,

        @NotEmpty(message = "Password is required")
        String password
) {
}
