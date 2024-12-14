package com.jonichi.envelope.auth.infrastructure.adapter.in;

import com.jonichi.envelope.auth.application.port.in.AuthUseCase;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthTokenDTO;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.RegisterRequestDTO;
import com.jonichi.envelope.common.dto.ApiResponse;
import com.jonichi.envelope.common.dto.SuccessResponse;
import jakarta.validation.Valid;
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

        String token = authUseCase.register(
                registerRequestDTO.username(),
                registerRequestDTO.email(),
                registerRequestDTO.password()
        );

        AuthTokenDTO authTokenDTO = new AuthTokenDTO(token);

        HttpStatus status = HttpStatus.CREATED;
        ApiResponse<AuthTokenDTO> response = SuccessResponse.<AuthTokenDTO>builder()
                .code(status.value())
                .message("User registered successfully")
                .data(authTokenDTO)
                .build();

        return ResponseEntity.status(status).body(response);
    }

}
