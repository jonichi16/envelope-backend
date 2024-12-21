package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.mapper.UserMapper;
import com.jonichi.envelope.auth.infrastructure.adapter.out.model.UserEntity;
import com.jonichi.envelope.common.util.listener.TransactionalHandler;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link UserRepositoryPort} interface for managing user data.
 *
 * <p>This class acts as an adapter in the hexagonal architecture, connecting the application
 * core to the database through JPA. It utilizes the {@link UserJpaRepository} for database
 * interactions and the {@link UserMapper} to convert between domain and persistence models.
 * Transactional operations are handled by the {@link TransactionalHandler}.</p>
 */
@Repository
public class UserRepository implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final TransactionalHandler transactionalHandler;

    /**
     * Constructs a new {@link UserRepository}.
     *
     * @param userJpaRepository the JPA repository for user entities
     * @param transactionalHandler the transactional handler for managing transactions
     */
    public UserRepository(
            UserJpaRepository userJpaRepository,
            TransactionalHandler transactionalHandler
    ) {
        this.userJpaRepository = userJpaRepository;
        this.transactionalHandler = transactionalHandler;
    }

    @Override
    public User save(User user) {
        Supplier<UserEntity> supplier = () -> userJpaRepository.save(UserMapper.toUserEntity(user));
        UserEntity userEntity = transactionalHandler.runInTransactionSupplier(supplier);

        return UserMapper.toUser(userEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserMapper::toUser)
                .or(Optional::empty);
    }
}
