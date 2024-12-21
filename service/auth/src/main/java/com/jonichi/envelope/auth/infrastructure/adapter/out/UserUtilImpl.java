package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.application.port.out.UserUtil;
import com.jonichi.envelope.auth.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implementation of the {@link UserUtil} interface for fetching user details.
 *
 * <p>This class provides the actual logic for retrieving the details of the currently
 * authenticated user from the security context. It uses the {@link UserRepository} to
 * fetch the user based on the authenticated username.</p>
 */
public class UserUtilImpl implements UserUtil {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@link UserUtilImpl} with the given {@link UserRepository}.
     *
     * @param userRepository the {@link UserRepository} to fetch user details
     */
    public UserUtilImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserDetails() {
        Object username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findByUsername((String) username).orElseThrow();
    }

}
