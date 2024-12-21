package com.jonichi.envelope.auth.infrastructure.adapter.in;

import com.jonichi.envelope.auth.application.port.in.AuthUseCase;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthTokenDTO;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthenticateRequestDTO;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.RegisterRequestDTO;
import com.jonichi.envelope.common.dto.ApiResponse;
import java.util.Objects;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthUseCase authUseCase;
    @InjectMocks
    private AuthController authController;


    @Test
    public void register_shouldCallAuthUseCaseOnce() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(username, email, password);

        // when
        authController.register(registerRequestDTO);

        // then
        verify(authUseCase, times(1)).register(
                registerRequestDTO.username(),
                registerRequestDTO.email(),
                registerRequestDTO.password()
        );
    }

    @Test
    public void register_shouldReturnCorrectResponseBody() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(username, email, password);
        AuthTokenDTO authTokenDTO = new AuthTokenDTO(1, "encodedToken");

        // when
        when(authUseCase.register(username, email, password)).thenReturn(authTokenDTO);
        ResponseEntity<ApiResponse<AuthTokenDTO>> response = authController.register(registerRequestDTO);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(Objects.requireNonNull(response.getBody()).getData().token()).isEqualTo("encodedToken");
        assertThat(Objects.requireNonNull(response.getBody()).getData().userId()).isEqualTo(1);
        assertThat(response.getBody().getMessage()).isEqualTo("User registered successfully");
        assertThat(response.getBody().getCode()).isEqualTo(201);

    }

    @Test
    public void authenticate_shouldCallAuthUseCaseOnce() throws Exception {
        // given
        String username = "test";
        String password = "secret";

        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO(username, password);

        // when
        authController.authenticate(authenticateRequestDTO);

        // then
        verify(authUseCase, times(1)).authenticate(
                authenticateRequestDTO.username(),
                authenticateRequestDTO.password()
        );
    }

    @Test
    public void authenticate_shouldReturnCorrectResponseBody() throws Exception {
        // given
        String username = "test";
        String password = "secret";

        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO(username, password);

        // when
        when(authUseCase.authenticate(username, password)).thenReturn("encodedToken");
        ResponseEntity<ApiResponse<AuthTokenDTO>> response = authController.authenticate(authenticateRequestDTO);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(Objects.requireNonNull(response.getBody()).getData().token()).isEqualTo("encodedToken");
        assertThat(response.getBody().getMessage()).isEqualTo("User authenticated successfully");
        assertThat(response.getBody().getCode()).isEqualTo(200);

    }

}