package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateResetPasswordRequestDTO(
        @NotBlank(message = "Informe seu email")
        @Email(message = "Email inválido")
        @Size(max = 250, message = "Seu email tem que ser menor do que 250 caracteres")
        String email
) { }
