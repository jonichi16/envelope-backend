package com.jonichi.envelope.auth.application.port.out.util;

public interface AuthenticationManagerPort {
    void authenticate(String username, String password);
}
