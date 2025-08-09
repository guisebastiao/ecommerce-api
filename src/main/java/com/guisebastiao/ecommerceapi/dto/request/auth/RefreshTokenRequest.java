package com.guisebastiao.ecommerceapi.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Informe o refresh token")
        String refreshToken
) { }
