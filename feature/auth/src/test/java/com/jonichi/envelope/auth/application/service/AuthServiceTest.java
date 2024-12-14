package com.jonichi.envelope.auth.application.service;

import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.application.port.out.util.JwtUtilPort;
import com.jonichi.envelope.auth.application.port.out.util.PasswordEncoderPort;
import com.jonichi.envelope.auth.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;
    @Mock
    private JwtUtilPort jwtUtilPort;

    @InjectMocks
    private AuthService authService;

    @Test
    public void register_withUserDetails_shouldReturnToken() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";
        String encodedPassword = "encodedPassword";
        User user = new User(username, email, encodedPassword);

        // when
        when(passwordEncoderPort.encode(password)).thenReturn(encodedPassword);
        when(jwtUtilPort.generateToken(user)).thenReturn("jwtToken");
        String token = authService.register(username, email, password);

        // then
        verify(passwordEncoderPort, times(1)).encode(password);
        verify(jwtUtilPort, times(1)).generateToken(user);
        assertThat(token).isEqualTo("jwtToken");
    }

    @Test
    public void register_withUserDetails_shouldSaveUser() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";
        String encodedPassword = "encodedPassword";

        // when
        when(passwordEncoderPort.encode(password)).thenReturn(encodedPassword);
        authService.register(username, email, password);

        // then
        verify(userRepositoryPort, times(1)).save(
                new User(username, email, encodedPassword)
        );
    }

}
