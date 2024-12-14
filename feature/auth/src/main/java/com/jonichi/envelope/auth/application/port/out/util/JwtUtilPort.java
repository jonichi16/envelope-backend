package com.jonichi.envelope.auth.application.port.out.util;

import com.jonichi.envelope.auth.domain.User;

public interface JwtUtilPort {
    String generateToken(User user);
}
