package com.jonichi.envelope.auth.application.service;

import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.application.port.out.util.AuthenticationManagerPort;
import com.jonichi.envelope.auth.application.port.out.util.JwtUtilPort;
import com.jonichi.envelope.auth.application.port.out.util.PasswordEncoderPort;
import com.jonichi.envelope.auth.domain.Role;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthTokenDTO;
import com.jonichi.envelope.common.exception.EnvelopeDuplicateException;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;
    @Mock
    private JwtUtilPort jwtUtilPort;
    @Mock
    private AuthenticationManagerPort authenticationManagerPort;

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
        when(userRepositoryPort.save(user)).thenReturn(new User(2, username, email, encodedPassword, Role.USER));
        when(passwordEncoderPort.encode(password)).thenReturn(encodedPassword);
        when(jwtUtilPort.generateToken(user)).thenReturn("jwtToken");
        AuthTokenDTO authTokenDTO = authService.register(username, email, password);

        // then
        verify(passwordEncoderPort, times(1)).encode(password);
        verify(jwtUtilPort, times(1)).generateToken(user);
        assertThat(authTokenDTO.token()).isEqualTo("jwtToken");
        assertThat(authTokenDTO.userId()).isEqualTo(2);
    }

    @Test
    public void register_shouldCheckForExistingUser() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";
        String encodedPassword = "encodedPassword";

        // when
        when(userRepositoryPort.save(new User(username, email, encodedPassword))).thenReturn(
                new User(1, username, email, encodedPassword, Role.USER)
        );
        when(passwordEncoderPort.encode(password)).thenReturn(encodedPassword);

        // then
        assertThatNoException().isThrownBy(() ->
                authService.register(username, email, password));
        verify(userRepositoryPort, times(1)).findByUsername(username);

    }

    @Test
    public void register_withDuplicate_shouldThrowEnvelopeDuplicateException() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";

        // when
        when(userRepositoryPort.findByUsername(username))
                .thenReturn(Optional.of(
                        new User(username, email, password)
                ));

        // then
        assertThatThrownBy(() -> authService.register(username, email, password))
                .isInstanceOf(EnvelopeDuplicateException.class)
                .hasMessage("Username already exists");
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
        when(userRepositoryPort.save(new User(username, email, encodedPassword))).thenReturn(
                new User(1, username, email, encodedPassword, Role.USER)
        );
        authService.register(username, email, password);

        // then
        verify(userRepositoryPort, times(1)).save(
                new User(username, email, encodedPassword)
        );
    }

    @Test
    public void authenticate_shouldReturnJwtToken() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";

        User user = new User(2, username, email, password, Role.USER);

        // when
        when(userRepositoryPort.findByUsername(username)).thenReturn(Optional.of(user));
        when(jwtUtilPort.generateToken(user)).thenReturn("encodedToken");
        AuthTokenDTO authTokenDTO = new AuthTokenDTO(2, "encodedToken");

        // then
        assertThat(authService.authenticate(username, password)).isEqualTo(authTokenDTO);
        verify(authenticationManagerPort, times(1)).authenticate(username, password);
    }

}
