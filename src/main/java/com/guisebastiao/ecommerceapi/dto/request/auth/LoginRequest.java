package com.guisebastiao.ecommerceapi.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Informe seu email")
        @Email(message = "Email inválido")
        @Size(max = 250, message = "Seu email tem que ser menor do que 250 caracteres")
        String email,

        @NotBlank(message = "Informe sua senha")
        @Size(max = 20, message = "Sua senha tem que ser menor do que 20 caracteres")
        @Size(min = 6, message = "Sua senha tem que ser maior do que 6 caracteres")
        @Pattern.List({
                @Pattern(regexp = ".*[A-Z].*", message = "Sua senha deve ter uma letra maiúscula"),
                @Pattern(regexp = ".*\\d.*\\d.*", message = "Sua senha deve ter dois números"),
                @Pattern(regexp = ".*[@$!%*?&.#].*", message = "Sua senha deve ter um caractere especial")
        })
        String password
) { }

