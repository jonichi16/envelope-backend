package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.application.port.out.util.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link PasswordEncoderPort} interface for encoding passwords.
 *
 * <p>The {@code PasswordEncoderImpl} class uses Spring Security's {@link PasswordEncoder} to
 * encode passwords. It is used to ensure secure password storage by hashing the password before
 * saving it to the database.</p>
 */
@Component
public class PasswordEncoderImpl implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for {@code PasswordEncoderImpl}.
     *
     * <p>This constructor initializes the {@link PasswordEncoder} to be used for encoding
     * passwords. It allows the use of a custom or default password encoder provided by Spring
     * Security.</p>
     *
     * @param passwordEncoder the {@link PasswordEncoder} to encode passwords
     */
    public PasswordEncoderImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
