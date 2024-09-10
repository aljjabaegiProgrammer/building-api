package com.geonlee.api.domin.rsa.record;

import lombok.Builder;

/**
 * @author GEONLEE
 * @since 2024-09-09
 */
@Builder
public record PublicKeyResponse(
        String publicKey
) {
}
