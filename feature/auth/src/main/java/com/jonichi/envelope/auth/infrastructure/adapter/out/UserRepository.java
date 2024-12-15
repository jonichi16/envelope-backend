package com.jonichi.envelope.auth.infrastructure.adapter.out;

import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.common.util.listener.TransactionalHandler;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private TransactionalHandler transactionalHandler;

    public void save(User user) {

        transactionalHandler.runInTransaction(() ->
                System.out.println("Test")
        );

    }
}
