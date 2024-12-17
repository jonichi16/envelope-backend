package com.jonichi.envelope.auth.infrastructure.adapter.out;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
public class AuthenticationManagerImplTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationManagerImpl authenticationManagerImpl;

    @Test
    public void authenticate_shouldReturnUser() throws Exception {
        // given
        String username = "test";
        String password = "secret";

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                "test",
                null,
                List.of(new SimpleGrantedAuthority("USER"))
        );

        // when
        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenReturn(mockAuthentication);
        authenticationManagerImpl.authenticate(username, password);

        // then
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertThat(mockAuthentication.isAuthenticated()).isTrue();
    }

}
