package com.jonichi.envelope.auth.application.port.out;

import com.jonichi.envelope.auth.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);

    Optional<User> findByUsername(String username);
}
