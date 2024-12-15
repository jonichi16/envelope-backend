package com.jonichi.envelope.auth.infrastructure.adapter.out.mapper;

import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Utility class for mapping between {@link User} and {@link UserEntity}, as well as
 * converting {@link User} to {@link UserDetails} for security purposes.
 *
 * <p>This class provides static methods to transform data between the domain model,
 * persistence model, and security model, ensuring compatibility across different layers
 * of the application.</p>
 */
public class UserMapper {

    /**
     * Converts a {@link User} domain object to a {@link UserEntity} persistence model.
     *
     * <p>This method is typically used when persisting a {@link User} object to the database.</p>
     *
     * @param user the {@link User} domain object to convert
     * @return the corresponding {@link UserEntity} persistence model
     */
    public static UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    /**
     * Converts a {@link UserEntity} persistence model to a {@link User} domain object.
     *
     * <p>This method is typically used when retrieving user data from the database
     * and mapping it to the domain model for use within the application.</p>
     *
     * @param userEntity the {@link UserEntity} persistence model to convert
     * @return the corresponding {@link User} domain object
     */
    public static User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }

    /**
     * Converts a {@link User} domain object to a {@link UserDetails} implementation.
     *
     * <p>This method is used to adapt the domain model to the Spring Security framework,
     * allowing authentication and authorization based on the {@link User} object.</p>
     *
     * @param user the {@link User} domain object to convert
     * @return a {@link UserDetails} implementation based on the provided {@link User}
     */
    public static UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
