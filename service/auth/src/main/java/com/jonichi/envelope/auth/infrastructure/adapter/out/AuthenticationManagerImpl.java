package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.application.port.out.util.AuthenticationManagerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link AuthenticationManagerPort} interface for authenticating users.
 *
 * <p>The {@code AuthenticationManagerImpl} class uses Spring Security's
 * {@link AuthenticationManager} to authenticate a user by validating their username and password.
 * It delegates the authentication process to Spring Security's built-in authentication
 * mechanisms.</p>
 */
@Component
public class AuthenticationManagerImpl implements AuthenticationManagerPort {

    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for {@code AuthenticationManagerImpl}.
     *
     * <p>This constructor initializes the {@link AuthenticationManager} used for authenticating
     * the user's credentials.</p>
     *
     * @param authenticationManager the {@link AuthenticationManager} to perform the authentication
     */
    public AuthenticationManagerImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }
}
