package com.jonichi.envelope.auth.application.port.out.util;

/**
 * A port for encoding passwords.
 *
 * <p>The {@code PasswordEncoderPort} interface defines the contract for encoding
 * a user's password before storing or comparing it for authentication.</p>
 */
public interface PasswordEncoderPort {

    /**
     * Encodes a raw password.
     *
     * <p>This method takes a plain-text password and returns an encoded version
     * to be securely stored or compared during authentication.</p>
     *
     * @param password the raw password to encode
     * @return the encoded version of the provided password
     */
    String encode(String password);
}
