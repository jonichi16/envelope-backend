package com.jonichi.envelope.auth.application.port.out;

import com.jonichi.envelope.auth.domain.User;
import java.util.Optional;

/**
 * Port interface for user repository operations.
 *
 * <p>This interface defines the contract for interacting with the user repository.
 * Implementations of this interface should handle data persistence and retrieval
 * related to user entities.</p>
 */
public interface UserRepositoryPort {

    /**
     * Saves the given user entity to the repository.
     *
     * <p>If the user entity already exists, this method updates the existing record.
     * Otherwise, it creates a new record in the repository.</p>
     *
     * @param user the {@link User} entity to save
     * @return the saved {@link User} entity
     */
    User save(User user);

    /**
     * Finds a user entity by their username.
     *
     * <p>This method searches the repository for a user entity with the specified username.
     * If a matching user is found, it is returned as an {@link Optional}; otherwise, an empty
     * {@link Optional} is returned.</p>
     *
     * @param username the username of the user to find
     * @return an {@link Optional} containing the {@link User} if found, or empty if not
     */
    Optional<User> findByUsername(String username);
}
