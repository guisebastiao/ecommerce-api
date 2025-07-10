package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ActiveLoginRequestDTO(
        @NotNull(message = "Informe o id do cliente")
        UUID clientId,

        @NotBlank(message = "Informe o email")
        @Email(message = "Email inválido")
        @Size(max = 250, message = "Seu email tem que ser menor do que 250 caracteres")
        String email,

        @NotBlank(message = "Informe o código de verificação")
        @Pattern(regexp = "\\d{6}", message = "Código de verificação inválido")
        String verificationCode
) { }
