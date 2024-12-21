package com.jonichi.envelope.auth.infrastructure.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A Data Transfer Object (DTO) for representing authentication tokens.
 *
 * <p>The {@code AuthTokenDTO} class encapsulates a token string and is used in authentication
 * responses. It ensures a consistent structure for token-related data.</p>
 *
 * <p>Fields that are {@code null} will be excluded from the JSON serialization due to the
 * {@link JsonInclude} annotation.</p>
 *
 * @param token the authentication token string
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthTokenDTO(
        Integer userId,
        String token
) {
}
