package com.jonichi.envelope.auth.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A Data Transfer Object (DTO) for representing authentication tokens.
 *
 * <p>The {@code AuthTokenDTO} class encapsulates a accessToken string and is used in authentication
 * responses. It ensures a consistent structure for accessToken-related data.</p>
 *
 * <p>Fields that are {@code null} will be excluded from the JSON serialization due to the
 * {@link JsonInclude} annotation.</p>
 *
 * @param accessToken the authentication accessToken string
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthTokenDTO(
        Integer userId,
        String accessToken
) {
}
