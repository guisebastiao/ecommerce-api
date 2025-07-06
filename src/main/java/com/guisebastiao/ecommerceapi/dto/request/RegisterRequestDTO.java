package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record RegisterRequestDTO(
        @NotBlank(message = "Informe seu nome")
        @Size(max = 100, message = "Seu nome tem que ser menor de 100 caracteres")
        @Size(min = 3, message = "Seu nome tem que ser meior do que 3 caracteres")
        String name,

        @NotBlank(message = "Informe seu nome")
        @Size(max = 100, message = "Seu sobrenome tem que ser menor de 100 caracteres")
        @Size(min = 3, message = "Seu sobrenome tem que ser meior do que 3 caracteres")
        String surname,

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
        String password,

        @NotBlank(message = "Informe seu CPF")
        @CPF(message = "Seu CPF está inválido")
        String cpf,

        @NotBlank(message = "Informe seu número de telefone")
        @Pattern(regexp = "\\d{13}", message = "Número de telefone inválido")
        String phone,

        @NotNull(message = "Informe sua data de nascimento")
        @Past(message = "Sua data de nascimento deve estar no passado")
        LocalDate birth
) { }

