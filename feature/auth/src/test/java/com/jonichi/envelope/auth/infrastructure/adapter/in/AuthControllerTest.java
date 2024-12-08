package com.jonichi.envelope.auth.infrastructure.adapter.in;

import com.jonichi.envelope.auth.application.port.in.AuthUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthUseCase authUseCase;
    @InjectMocks
    private AuthController authController;

    @Test
    public void register_shouldCallAuthUseCaseOnce() throws Exception {
        // given

        // when
        when(authUseCase.register()).thenReturn("encodedToken");
        ResponseEntity<String> response = authController.register();

        // then
        verify(authUseCase, times(1)).register();
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo("encodedToken");
    }

}