package com.guisebastiao.ecommerceapi.dto.request;

import com.guisebastiao.ecommerceapi.validation.passwordMather.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatches
public record ResetPasswordRequestDTO(
        @NotBlank(message = "Informe sua nova senha")
        @Size(min = 6, message = "Sua nova senha deve ter mais de 6 caracteres")
        @Size(max = 20, message = "Sua nova senha deve ter menos de 20 caracteres")
        @Pattern.List({
                @Pattern(regexp = ".*[A-Z].*", message = "Sua nova senha deve ter uma letra maiúscula"),
                @Pattern(regexp = ".*\\d.*\\d.*", message = "Sua nova senha deve ter dois números"),
                @Pattern(regexp = ".*[@$!%*?&.#].*", message = "Sua nova senha deve ter um caractere especial")
        })
        String newPassword,

        @NotBlank(message = "Confirme sua nova senha")
        @Size(min = 6, message = "Sua senha deve ter mais de 6 caracteres")
        @Size(max = 20, message = "Sua senha deve ter menos de 20 caracteres")
        @Pattern.List({
                @Pattern(regexp = ".*[A-Z].*", message = "Sua senha deve ter uma letra maiúscula"),
                @Pattern(regexp = ".*\\d.*\\d.*", message = "Sua senha deve ter dois números"),
                @Pattern(regexp = ".*[@$!%*?&.#].*", message = "Sua senha deve ter um caractere especial")
        })
        String confirmPassword
) { }
