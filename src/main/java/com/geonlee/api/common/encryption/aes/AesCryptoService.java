package com.geonlee.api.common.encryption.aes;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.encryption.CryptoService;
import com.geonlee.api.common.exception.custom.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author GEONLEE
 * @since 2024-09-09
 */
@Configuration
@Slf4j
public class AesCryptoService implements CryptoService {
    private final String algorithm;
    private final int keySize;
    private final String aesKey;
    private final String encryptionMode;
    private final byte[] initialVector;

    public AesCryptoService(@Value("${aes.algorithm}") String algorithm,
                            @Value("${aes.key-size}") int keySize,
                            @Value("${aes.secret}") String aesKey,
                            @Value("${aes.mode}") String encryptionMode,
                            @Value("${aes.iv-size}") int ivSize) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.aesKey = aesKey;
        this.encryptionMode = encryptionMode;
        this.initialVector = new byte[ivSize];
        if (this.aesKey.isEmpty()) {
            log.error("AES key does not exist. create new key. ▼");
            createKey();
        }
    }

    @Override
    public void createKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(this.algorithm);
        keyGen.init(this.keySize);
        SecretKey aesKey = keyGen.generateKey();
        log.info("=================================AES KEY=================================");
        log.info("AES key : {}", Base64.getEncoder().encodeToString(aesKey.getEncoded()));
    }

    @Override
    public SecretKey getPublicKey() {
        byte[] decodeKey = Base64.getDecoder().decode(this.aesKey);
        return new SecretKeySpec(decodeKey, 0, decodeKey.length, this.algorithm);
    }

    @Override
    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(this.encryptionMode);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(this.initialVector);
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey(), ivParameterSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error("Decryption error. encryptedText : {}", encryptedText);
            throw new ServiceException(ErrorCode.SERVICE_ERROR, e);
        }
    }

    @Override
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(this.encryptionMode);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(this.initialVector);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(), ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new ServiceException(ErrorCode.SERVICE_ERROR, e);
        }
    }
}
