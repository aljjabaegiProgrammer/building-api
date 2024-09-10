package com.geonlee.api.common.encryption;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author GEONLEE
 * @since 2024-09-10
 */
public interface CryptoService {

    void createKey() throws NoSuchAlgorithmException;

    Key getPublicKey();

    String decrypt(String encryptedText);

    String encrypt(String plainText);
}
