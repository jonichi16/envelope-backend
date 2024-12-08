package com.jonichi.envelope.auth.infrastructure.adapter.in.dto;

public record RegisterRequestDTO(
        String username,
        String email,
        String password
) {
}
