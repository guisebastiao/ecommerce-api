package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(
        @NotBlank(message = "Informe o refresh token")
        String refreshToken
) { }
