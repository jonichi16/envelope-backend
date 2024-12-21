package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.domain.Role;
import com.jonichi.envelope.auth.domain.User;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class UserUtilImplTest {

    @Mock
    private Authentication auth;
    @Mock
    private UserRepository userRepository;

    private UserUtilImpl userUtil;

    @BeforeEach
    public void initSecurityContext() {
        userUtil = new UserUtilImpl(userRepository);
        when(auth.getPrincipal()).thenReturn("test");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getUserDetails_shouldReturnUser() throws Exception {
        // given
        User existingUser = new User(
                1,
                "test",
                "test@mail.com",
                "secret",
                Role.USER
        );

        // when
        when(userRepository.findByUsername("test")).thenReturn(
                Optional.of(existingUser)
        );
        User user = userUtil.getUserDetails();

        // then
        verify(userRepository, times(1)).findByUsername("test");
        assertThat(user).isEqualTo(existingUser);
    }
}
