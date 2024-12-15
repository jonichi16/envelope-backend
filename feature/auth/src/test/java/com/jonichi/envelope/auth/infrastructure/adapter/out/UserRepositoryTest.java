package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.mapper.UserMapper;
import com.jonichi.envelope.auth.infrastructure.adapter.out.model.UserEntity;
import com.jonichi.envelope.common.util.listener.TransactionalHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void save_shouldBeInTransactional() throws Exception {
        // given
        User user = new User("test", "test@mail.com", "secret");

        // when
        userRepository.save(user);

        // then
        verify(transactionalHandler, times(1)).runInTransaction(any(Runnable.class));
    }

    @Test
    public void save_shouldInvokeUserJpaRepository() throws Exception {
        // given
        User user = new User("test", "test@mail.com", "secret");

        // when
        doAnswer(invocation -> {
            Runnable action = invocation.getArgument(0);
            action.run();
            return null;
        }).when(transactionalHandler).runInTransaction(any(Runnable.class));

        userRepository.save(user);
        UserEntity userEntity = UserMapper.toUserEntity(user);

        // then
        verify(userJpaRepository, times(1)).save(userEntity);
    }

}
