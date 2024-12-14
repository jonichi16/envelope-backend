package com.jonichi.envelope.auth.application.port.out.util;

public interface PasswordEncoderPort {
    String encode(String password);
}
