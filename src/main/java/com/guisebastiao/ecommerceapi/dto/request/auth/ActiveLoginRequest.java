package com.guisebastiao.ecommerceapi.dto.request.auth;

import jakarta.validation.constraints.*;

public record ActiveLoginRequest(
        @NotBlank(message = "Informe o codigo")
        String code,

        @NotBlank(message = "Informe o código de verificação")
        @Pattern(regexp = "\\d{6}", message = "Código de verificação inválido")
        String verificationCode
) { }
