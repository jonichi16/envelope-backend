package com.jonichi.envelope.common.exception;

/**
 * Custom exception class representing a duplicate entity scenario in the Envelope application.
 *
 * <p>The {@code EnvelopeDuplicateException} is thrown when a conflict occurs due to
 * duplicate entities, such as when attempting to create a user or resource that already exists.</p>
 */
public class EnvelopeDuplicateException extends RuntimeException {

    /**
     * Constructs a new {@code EnvelopeDuplicateException} with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public EnvelopeDuplicateException(String message) {
        super(message);
    }
}
