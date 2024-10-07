package com.jonichi.envelope.auth;

import lombok.Builder;

@Builder
public record AuthenticationResult(String token) {}

