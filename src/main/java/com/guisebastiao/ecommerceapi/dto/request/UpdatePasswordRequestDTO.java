package com.guisebastiao.ecommerceapi.dto.request;

import com.guisebastiao.ecommerceapi.validation.passwordMather.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatches
public record UpdatePasswordRequestDTO(
        @NotBlank(message = "Informe sua senha")
        @Size(max = 20, message = "Sua senha tem que ser menor do que 20 caracteres")
        @Size(min = 6, message = "Sua senha tem que ser maior do que 6 caracteres")
        @Pattern.List({
                @Pattern(regexp = ".*[A-Z].*", message = "Sua senha deve ter uma letra maiúscula"),
                @Pattern(regexp = ".*\\d.*\\d.*", message = "Sua senha deve ter dois números"),
                @Pattern(regexp = ".*[@$!%*?&.#].*", message = "Sua senha deve ter um caractere especial")
        })
        String currentPassword,

        @NotBlank(message = "Informe sua nova senha")
        @Size(max = 20, message = "Sua nova senha tem que ser menor do que 20 caracteres")
        @Size(min = 6, message = "Sua nova senha tem que ser maior do que 6 caracteres")
        @Pattern.List({
                @Pattern(regexp = ".*[A-Z].*", message = "Sua senha deve ter uma letra maiúscula"),
                @Pattern(regexp = ".*\\d.*\\d.*", message = "Sua senha deve ter dois números"),
                @Pattern(regexp = ".*[@$!%*?&.#].*", message = "Sua senha deve ter um caractere especial")
        })
        String newPassword,

        @NotBlank(message = "Confirme sua nova senha")
        @Size(max = 20, message = "Sua nova senha tem que ser menor do que 20 caracteres")
        @Size(min = 6, message = "Sua nova senha tem que ser maior do que 6 caracteres")
        @Pattern.List({
                @Pattern(regexp = ".*[A-Z].*", message = "Sua senha deve ter uma letra maiúscula"),
                @Pattern(regexp = ".*\\d.*\\d.*", message = "Sua senha deve ter dois números"),
                @Pattern(regexp = ".*[@$!%*?&.#].*", message = "Sua senha deve ter um caractere especial")
        })
        String confirmPassword
) { }
