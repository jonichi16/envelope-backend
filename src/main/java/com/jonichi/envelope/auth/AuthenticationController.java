package com.jonichi.envelope.auth;

import com.jonichi.envelope.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResult>> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        logger.info("Start - register");
        if (logger.isDebugEnabled()) {
            logger.debug("Request: email={}, name={}", request.email(), request.name());
        }

        final String successMessage = "User registered successfully.";
        final HttpStatus status = HttpStatus.CREATED;
        final AuthenticationResult result = service.register(request);
        final ApiResponse<AuthenticationResult> response = ApiResponse.Success.<AuthenticationResult>builder()
                .message(successMessage)
                .code(status.value())
                .data(result)
                .build();

        logger.info("End - register");
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationResult>> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        logger.info("Start - authenticate");
        if (logger.isDebugEnabled()) {
            logger.debug("Request: email={}", request.email());
        }

        final String successMessage = "User authenticated successfully.";
        final AuthenticationResult result = service.authenticate(request);
        final ApiResponse<AuthenticationResult> response = ApiResponse.Success.<AuthenticationResult>builder()
                .message(successMessage)
                .code(HttpStatus.OK.value())
                .data(result)
                .build();

        logger.info("End - authenticate");
        return ResponseEntity.ok(response);
    }

}
