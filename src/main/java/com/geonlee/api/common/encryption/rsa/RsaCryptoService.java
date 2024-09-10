package com.geonlee.api.common.encryption.rsa;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.encryption.CryptoService;
import com.geonlee.api.common.exception.custom.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author GEONLEE
 * @since 2024-09-09
 */
@Configuration
@Slf4j
public class RsaCryptoService implements CryptoService {
    private final String algorithm;
    private final int keySize;
    private final String privateKey;
    private final String publicKey;

    public RsaCryptoService(@Value("${rsa.algorithm}") String algorithm,
                            @Value("${rsa.key-size}") int keySize,
                            @Value("${rsa.private:}") String privateKey,
                            @Value("${rsa.public:}") String publicKey) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        if (this.privateKey.isEmpty() || this.publicKey.isEmpty()) {
            log.error("RSA public or private key does not exist. create new key. ▼");
            createKey();
        }
    }

    @Override
    public void createKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.algorithm);
        keyPairGenerator.initialize(this.keySize, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        log.info("=================================RSA KEY=================================");
        log.info("private : {}", Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        log.info("public : {}", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
    }

    private PrivateKey getPrivateKey() {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(this.privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            return keyFactory.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ServiceException(ErrorCode.SERVICE_ERROR, e);
        }
    }

    @Override
    public PublicKey getPublicKey() {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(this.publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            return keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NullPointerException e) {
            throw new ServiceException(ErrorCode.SERVICE_ERROR, e);
        }
    }

    @Override
    public String decrypt(String encryptedText) {
        PrivateKey privateKey = getPrivateKey();
        try {
            byte[] byteEncrypted = Base64.getDecoder().decode(encryptedText.getBytes());
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytePlain = cipher.doFinal(byteEncrypted);
            return new String(bytePlain, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException | NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("RSA decrypt error. encrypted value : {}", encryptedText);
            throw new ServiceException(ErrorCode.INVALID_PARAMETER, e);
        }
    }

    @Override
    public String encrypt(String plainText) {
        PublicKey publicKey = getPublicKey();
        try {
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytePlain = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(bytePlain);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new ServiceException(ErrorCode.SERVICE_ERROR, e);
        }
    }
}
