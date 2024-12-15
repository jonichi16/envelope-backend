package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.infrastructure.adapter.out.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
}
