package com.jonichi.envelope.auth.application.port.out;

import com.jonichi.envelope.auth.domain.User;

/**
 * Interface for utility methods related to the user.
 *
 * <p>This interface provides methods for fetching user details based on the current
 * authentication context.</p>
 */
public interface UserUtil {

    /**
     * Retrieves the details of the currently authenticated user.
     *
     * @return the {@link User} details of the authenticated user
     */
    User getUserDetails();
}
