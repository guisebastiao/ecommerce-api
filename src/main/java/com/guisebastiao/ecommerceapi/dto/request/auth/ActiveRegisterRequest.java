package com.guisebastiao.ecommerceapi.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public record ActiveRegisterRequest(
        @NotBlank(message = "Informe o código de verificação")
        String verificationCode
) { }
