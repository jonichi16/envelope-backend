package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.mapper.UserMapper;
import com.jonichi.envelope.common.util.listener.TransactionalHandler;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements UserRepositoryPort {

    private UserJpaRepository userJpaRepository;
    private TransactionalHandler transactionalHandler;

    @Override
    public void save(User user) {
        transactionalHandler.runInTransaction(() ->
                userJpaRepository.save(UserMapper.toUserEntity(user))
        );

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserMapper::toUser)
                .or(Optional::empty);
    }
}
