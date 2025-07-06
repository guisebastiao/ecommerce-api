package com.guisebastiao.ecommerceapi.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ActiveLoginResponseDTO(
        String accessToken,
        String tokenType,
        String refreshToken,
        Instant expiresAt,
        UUID id,
        String name,
        String email
) { }
