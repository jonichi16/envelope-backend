package com.jonichi.envelope.auth.infrastructure.adapter.out;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class PasswordEncoderImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private PasswordEncoderImpl passwordEncoderImpl;

    @Test
    public void encode_shouldReturnEncodedString() throws Exception {
        // given
        String password = "secret";
        String encodedPassword = "encodedPassword";

        // when
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // then
        assertThat(passwordEncoderImpl.encode(password)).isEqualTo(encodedPassword);
        verify(passwordEncoder, times(1)).encode(password);
    }
}
