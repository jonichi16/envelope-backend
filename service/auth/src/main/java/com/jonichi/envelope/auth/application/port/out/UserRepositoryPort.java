package com.jonichi.envelope.auth.application.port.out;

import com.jonichi.envelope.auth.domain.User;
import java.util.Optional;

/**
 * A port for managing user persistence operations in the application.
 *
 * <p>The {@code UserRepositoryPort} defines the contract for the operations
 * required to manage user entities in the persistence layer.</p>
 */
public interface UserRepositoryPort {

    /**
     * Saves a {@link User} entity to the persistence layer.
     *
     * @param user the {@link User} entity to save
     */
    void save(User user);

    /**
     * Finds a {@link User} entity by its username.
     *
     * @param username the username of the {@link User} to find
     * @return an {@link Optional} containing the {@link User} if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
}
