package com.jonichi.envelope.auth.infrastructure.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthTokenDTO(
        String token
) {
}
