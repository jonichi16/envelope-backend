package com.jonichi.envelope.auth.infrastructure.adapter.in;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postRegisterEndpointShouldReturn201Created() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register"))
                .andExpect(status().isCreated());
    }

}
