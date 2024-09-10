package com.geonlee.api.common.encryption.hybrid;

import com.geonlee.api.common.encryption.CryptoService;
import com.geonlee.api.common.encryption.aes.AesCryptoService;
import com.geonlee.api.common.encryption.rsa.RsaCryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author GEONLEE
 * @since 2024-09-10
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class HybridCryptoService implements CryptoService {

    private final RsaCryptoService rsaCryptoService;
    private final AesCryptoService aesCryptoService;

    @Override
    @Deprecated
    public void createKey() throws NoSuchAlgorithmException {

    }

    @Override
    public Key getPublicKey() {
        return this.rsaCryptoService.getPublicKey();
    }

    @Override
    public String decrypt(String encryptedText) {
        return this.aesCryptoService.decrypt(encryptedText);
    }

    @Override
    public String encrypt(String plainText) {
        return this.aesCryptoService.encrypt(plainText);
    }

    public String getEncryptedAesKey() {
        SecretKey secretKey = this.aesCryptoService.getPublicKey();
        String secretKeyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        return this.rsaCryptoService.encrypt(secretKeyString);
    }
}
