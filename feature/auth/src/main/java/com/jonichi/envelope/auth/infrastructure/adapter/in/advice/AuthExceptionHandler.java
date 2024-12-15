package com.jonichi.envelope.auth.infrastructure.adapter.in.advice;

import com.jonichi.envelope.common.constant.ErrorCode;
import com.jonichi.envelope.common.dto.ApiResponse;
import com.jonichi.envelope.common.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for authentication-related errors in the system.
 *
 * <p>This class intercepts exceptions related to authentication and provides custom
 * error responses. Specifically, it handles the {@link BadCredentialsException} and
 * returns a structured response indicating an invalid username or password.</p>
 *
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

    /**
     * Handles {@link BadCredentialsException} thrown during authentication.
     *
     * <p>This method intercepts {@link BadCredentialsException} and returns a response indicating
     * that the provided username or password is invalid. </p>
     *
     * @param e the {@link BadCredentialsException} thrown during authentication
     * @return a {@link ResponseEntity} containing an {@link ApiResponse} with an error message and
     *     status code 401 (UNAUTHORIZED)
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException e) {

        logger.error(e.getMessage(), e);

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiResponse<Void> response = ErrorResponse.<Void>builder()
                .code(status.value())
                .message(e.getMessage())
                .errorCode(ErrorCode.UNAUTHORIZED)
                .build();

        return ResponseEntity.status(status).body(response);

    }
}
