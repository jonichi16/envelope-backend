package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.domain.Role;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.mapper.UserMapper;
import com.jonichi.envelope.auth.infrastructure.adapter.out.model.UserEntity;
import com.jonichi.envelope.common.util.listener.TransactionalHandler;
import java.util.Optional;
import java.util.function.Supplier;
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

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private TransactionalHandler transactionalHandler;
    @InjectMocks
    private UserRepository userRepository;

    @Test
    public void save_shouldBeInTransactionalSupplierAndReturnUser() throws Exception {
        // given
        User user = new User("test", "test@mail.com", "secret");

        UserEntity userEntity = new UserEntity(1, "test", "test@mail.com", "secret", Role.USER);

        // when
        when(transactionalHandler.runInTransactionSupplier(any(Supplier.class))).thenReturn(userEntity);
        User createdUser = userRepository.save(user);

        // then
        verify(transactionalHandler, times(1)).runInTransactionSupplier(any(Supplier.class));
        assertThat(createdUser.getId()).isEqualTo(1);
        assertThat(createdUser.getUsername()).isEqualTo("test");
    }

    @Test
    public void save_shouldInvokeUserJpaRepository() throws Exception {
        // given
        User user = new User("test", "test@mail.com", "secret");

        // when
        UserEntity mockUserEntity = new UserEntity(1, "test", "test@mail.com", "secret", Role.USER);
        when(transactionalHandler.runInTransactionSupplier(any(Supplier.class)))
                .thenAnswer(invocation -> {
                    Supplier<UserEntity> supplier = invocation.getArgument(0);
                    return supplier.get();
                });
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);
        userRepository.save(user);

        // then
        verify(userJpaRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void findByUsername_shouldFindUser() throws Exception {
        // given
        String username = "test";
        String email = "test@mail.com";
        String password = "secret";
        User expectedUser = new User(1, username, email, password, Role.USER);

        // when
        when(userJpaRepository.findByUsername(username)).thenReturn(
                Optional.of(UserMapper.toUserEntity(expectedUser))
        );
        Optional<User> user = userRepository.findByUsername(username);

        // then
        verify(userJpaRepository, times(1)).findByUsername(username);
        assertThat(user).isPresent();
    }

    @Test
    public void findByUsername_withNoUser_shouldReturnOptionalEmpty() throws Exception {
        // given
        String username = "invalidUser";

        // when
        when(userJpaRepository.findByUsername(username)).thenReturn(
                Optional.empty()
        );
        Optional<User> user = userRepository.findByUsername(username);

        // then
        assertThat(user).isEmpty();
    }
}
