package com.guisebastiao.ecommerceapi.dto.response;

import java.time.Instant;

public record RefreshTokenResponseDTO(
        String accessToken,
        Instant expiresAt
){ }