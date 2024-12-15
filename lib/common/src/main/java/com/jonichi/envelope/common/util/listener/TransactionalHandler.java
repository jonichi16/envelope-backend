package com.jonichi.envelope.common.util.listener;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class to handle running code within a transaction context.
 *
 * <p>The {@code TransactionalHandler} class provides functionality to run operations within
 * a transactional boundary. It ensures that the provided runnable is executed in a Spring-managed
 * transaction with the appropriate propagation behavior.</p>
 */
@Service
public class TransactionalHandler {

    /**
     * Runs the provided {@code Runnable} in a transaction.
     *
     * <p>This method ensures that the {@code Runnable} is executed within a Spring transaction.
     * The transaction is started with {@code REQUIRED} propagation, meaning it will join an
     * existing transaction if one exists, or create a new transaction if none exists.</p>
     *
     * @param runnable the operation to run within the transaction context
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void runInTransaction(Runnable runnable) {
        TransactionEventListener.registerTransactionEvents();
        runnable.run();
    }

}
