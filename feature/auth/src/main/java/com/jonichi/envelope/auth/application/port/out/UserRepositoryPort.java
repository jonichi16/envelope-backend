package com.jonichi.envelope.auth.application.port.out;

import com.jonichi.envelope.auth.domain.User;

public interface UserRepositoryPort {
    void save(User user);

    void findByUsername(String username);
}
