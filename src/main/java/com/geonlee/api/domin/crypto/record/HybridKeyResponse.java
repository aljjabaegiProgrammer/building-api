package com.geonlee.api.domin.crypto.record;

import lombok.Builder;

/**
 * @author GEONLEE
 * @since 2024-09-10
 */
@Builder
public record HybridKeyResponse(
        String publicKey,
        String encryptedKey
) {
}
