package com.jonichi.envelope.auth.infrastructure.adapter.in;

import com.jonichi.envelope.auth.application.port.in.AuthUseCase;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthTokenDTO;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthenticateRequestDTO;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.RegisterRequestDTO;
import com.jonichi.envelope.common.dto.ApiResponse;
import com.jonichi.envelope.common.dto.SuccessResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling user authentication-related endpoints.
 *
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthUseCase authUseCase;

    /**
     * Constructs a new {@code AuthController} with the specified {@link AuthUseCase}.
     *
     * @param authUseCase the service that handles authentication-related business logic
     */
    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    /**
     * Registers a new user and returns an authentication token.
     *
     * <p>This endpoint accepts a {@link RegisterRequestDTO} containing the user's
     * registration details (username, email, and password). It delegates the registration
     * process to the {@link AuthUseCase} and returns an authentication token on success.</p>
     *
     * @param registerRequestDTO the registration request data containing username, email, and
     *     password
     * @return a {@link ResponseEntity} containing a {@link ApiResponse} with an authentication
     *     token
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthTokenDTO>> register(
            @RequestBody @Valid RegisterRequestDTO registerRequestDTO
    ) {
        logger.info("Start - Controller - register");
        logger.debug(
                "Request: username={}, email={}, password={}",
                registerRequestDTO.username(),
                registerRequestDTO.email(),
                "******"
        );

        AuthTokenDTO authTokenDTO = authUseCase.register(
                registerRequestDTO.username(),
                registerRequestDTO.email(),
                registerRequestDTO.password()
        );

        HttpStatus status = HttpStatus.CREATED;
        ApiResponse<AuthTokenDTO> response = SuccessResponse.<AuthTokenDTO>builder()
                .code(status.value())
                .message("User registered successfully")
                .data(authTokenDTO)
                .build();

        logger.info("End - Controller - register");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Endpoint for authenticating a user and generating an authentication token.
     *
     * <p>This method receives a request with the user's username and password, authenticates the
     * user, and returns a JWT token on successful authentication. The token can then be used for
     * subsequent requests that require authentication.</p>
     *
     * @param authenticateRequestDTO the request data containing the user's username and password
     * @return a {@link ResponseEntity} containing a {@link ApiResponse} with the authentication
     *     token
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthTokenDTO>> authenticate(
            @RequestBody @Valid AuthenticateRequestDTO authenticateRequestDTO
    ) {
        logger.info("Start - Controller - authenticate");
        logger.debug(
                "Request: username={}, password={}",
                authenticateRequestDTO.username(),
                "******"
        );

        String token = authUseCase.authenticate(
                authenticateRequestDTO.username(),
                authenticateRequestDTO.password()
        );

        AuthTokenDTO authTokenDTO = new AuthTokenDTO(1, token);

        HttpStatus status = HttpStatus.OK;
        ApiResponse<AuthTokenDTO> response = SuccessResponse.<AuthTokenDTO>builder()
                .code(status.value())
                .message("User authenticated successfully")
                .data(authTokenDTO)
                .build();

        logger.info("End - Controller - authenticate");
        return ResponseEntity.status(status).body(response);
    }

}
