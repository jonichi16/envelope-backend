package com.jonichi.envelope.auth.infrastructure.adapter.in.advice;

import com.jonichi.envelope.common.constant.ErrorCode;
import com.jonichi.envelope.common.dto.ApiResponse;
import java.util.Objects;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

public class AuthExceptionHandlerTest {

    private final AuthExceptionHandler authExceptionHandler = new AuthExceptionHandler();

    @Test
    public void handleBadCredentialsException_shouldReturnBadRequestError() throws Exception {
        // given
        BadCredentialsException exception = new BadCredentialsException("Invalid username or password");

        // when
        ResponseEntity<ApiResponse<Void>> response = authExceptionHandler.handleBadCredentials(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(Objects.requireNonNull(response.getBody()).getCode()).isEqualTo(401);
        assertThat(response.getBody().isSuccess()).isFalse();
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid username or password");
        assertThat(response.getBody().getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED);
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }
}
