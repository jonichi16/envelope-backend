package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.application.port.out.UserRepositoryPort;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.mapper.UserMapper;
import com.jonichi.envelope.common.util.listener.TransactionalHandler;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link UserRepositoryPort} interface for managing user data.
 *
 * <p>This class acts as a bridge between the application core and the infrastructure layer,
 * using JPA for data persistence. It leverages a {@link TransactionalHandler} to manage
 * transactional operations and a {@link UserMapper} for converting between domain and
 * persistence models.</p>
 */
@Repository
public class UserRepository implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final TransactionalHandler transactionalHandler;

    /**
     * Constructs a {@link UserRepository} with required dependencies.
     *
     * @param userJpaRepository the JPA repository for direct database interactions
     * @param transactionalHandler the handler to ensure transactional integrity
     */
    public UserRepository(
            UserJpaRepository userJpaRepository,
            TransactionalHandler transactionalHandler
    ) {
        this.userJpaRepository = userJpaRepository;
        this.transactionalHandler = transactionalHandler;
    }

    /**
     * Saves a {@link User} entity to the database.
     *
     * <p>This method ensures that the save operation runs within a transactional context
     * provided by the {@link TransactionalHandler}. The {@link User} domain object is
     * converted to its corresponding persistence model using the {@link UserMapper}.</p>
     *
     * @param user the {@link User} entity to save
     */
    @Override
    public void save(User user) {
        transactionalHandler.runInTransaction(() ->
                userJpaRepository.save(UserMapper.toUserEntity(user))
        );

    }

    /**
     * Finds a user by their username.
     *
     * <p>This method queries the database for a user with the specified username
     * and maps the result to a {@link User} domain object using the {@link UserMapper}.
     * If no user is found, an empty {@link Optional} is returned.</p>
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the {@link User} if found, or empty if not
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserMapper::toUser)
                .or(Optional::empty);
    }
}
