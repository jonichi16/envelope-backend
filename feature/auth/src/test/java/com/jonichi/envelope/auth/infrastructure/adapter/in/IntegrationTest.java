package com.jonichi.envelope.auth.infrastructure.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonichi.envelope.auth.application.port.in.AuthUseCase;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.AuthenticateRequestDTO;
import com.jonichi.envelope.auth.infrastructure.adapter.in.dto.RegisterRequestDTO;
import com.jonichi.envelope.common.advice.GlobalExceptionHandler;
import com.jonichi.envelope.common.exception.EnvelopeDuplicateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(GlobalExceptionHandler.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUseCase authUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void register_shouldReturn201Created() throws Exception {
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(username, email, password);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void register_withInvalidEmail_shouldReturn400BadRequestError() throws Exception {
        // given
        String invalidRequest = "{ \"username\": \"test\", " +
                "\"email\": \"invalidEmail\", " +
                "\"password\": \"12345\" }";

        // when, then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_withMissingFields_shouldReturn400BadRequestError() throws Exception {
        // given
        String invalidRequest = "{ \"email\": \"test@mail.com\" }";

        // when, then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_withDuplicateUsername_shouldReturn400BadRequestError() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";

        // when
        when(authUseCase.register(username, email, password)).thenThrow(
                new EnvelopeDuplicateException("Username already exists")
        );
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(username, email, password);

        // then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_withError_shouldReturn500InternalServerError() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";

        // when
        when(authUseCase.register(username, email, password)).thenThrow(
                new RuntimeException("Something went wrong")
        );
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(username, email, password);

        // then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void authenticate_shouldReturn20OK() throws Exception {
        String username = "test";
        String password = "secret";

        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO(username, password);

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticateRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void authenticate_withMissingFields_shouldReturn400BadRequestError() throws Exception {
        // given
        String invalidRequest = "{ }";

        // when, then
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void authenticate_withError_shouldReturn500InternalServerError() throws Exception {
        // given
        String username = "test";
        String password = "secret";

        // when
        when(authUseCase.authenticate(username, password)).thenThrow(
                new RuntimeException("Something went wrong")
        );
        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO(username, password);

        // then
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticateRequestDTO)))
                .andExpect(status().isInternalServerError());
    }

}
