package com.geonlee.api.domin.rsa;

import com.geonlee.api.common.code.NormalCode;
import com.geonlee.api.common.encryption.CryptoService;
import com.geonlee.api.common.encryption.aes.AesCryptoService;
import com.geonlee.api.common.encryption.rsa.RsaCryptoService;
import com.geonlee.api.common.response.ItemResponse;
import com.geonlee.api.config.message.MessageConfig;
import com.geonlee.api.domin.rsa.record.PublicKeyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author GEONLEE
 * @since 2024-09-09
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "암호화 관련 정보 요청", description = "보안이 필요한 정보를 암호화 하기 위한 키 요청")
@RequestMapping("v1")
public class CryptoController {
    private final CryptoService rsaCryptoService;
    private final CryptoService aesCryptoService;
    private final MessageConfig messageConfig;

    @Operation(summary = "RSA Public Key 요청", description = """
            """, operationId = "API-999-01")
    @PostMapping(value = "/key/rsa", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemResponse<PublicKeyResponse>> getPublicKey() {
        PublicKey key = (PublicKey) this.rsaCryptoService.getPublicKey();
        String keyString = Base64.getEncoder().encodeToString(key.getEncoded());
        PublicKeyResponse publicKeyResponse = PublicKeyResponse.builder().publicKey(keyString).build();
        return ResponseEntity.ok()
                .body(ItemResponse.<PublicKeyResponse>builder()
                        .status(messageConfig.getCode(NormalCode.SEARCH_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.SEARCH_SUCCESS))
                        .item(publicKeyResponse)
                        .build());
    }

    @Operation(summary = "AES Key 요청", description = """
            """, operationId = "API-999-02")
    @PostMapping(value = "/key/aes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemResponse<PublicKeyResponse>> getAESPublicKey() {
        SecretKey key = (SecretKey) this.aesCryptoService.getPublicKey();
        String keyString = Base64.getEncoder().encodeToString(key.getEncoded());
        PublicKeyResponse publicKeyResponse = PublicKeyResponse.builder().publicKey(keyString).build();
        return ResponseEntity.ok()
                .body(ItemResponse.<PublicKeyResponse>builder()
                        .status(messageConfig.getCode(NormalCode.SEARCH_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.SEARCH_SUCCESS))
                        .item(publicKeyResponse)
                        .build());
    }
}
