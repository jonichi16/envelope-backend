package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.infrastructure.adapter.out.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on {@link UserEntity}.
 *
 * <p>The {@code UserJpaRepository} extends Spring Data JPA's {@link JpaRepository},
 * providing CRUD operations and custom query methods for the {@link UserEntity}.</p>
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Finds a user by their username.
     *
     * <p>This method queries the database for a {@link UserEntity} with the specified
     * username.</p>
     *
     * @param username the username of the user to find
     * @return an {@link Optional} containing the {@link UserEntity} if found, or empty if not
     */
    Optional<UserEntity> findByUsername(String username);
}
