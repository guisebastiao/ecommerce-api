package com.guisebastiao.ecommerceapi.dto.request.client;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateAccountRequest(
        @NotBlank(message = "Informe seu nome")
        @Size(max = 100, message = "Seu nome tem que ser menor de 100 caracteres")
        @Size(min = 3, message = "Seu nome tem que ser meior do que 3 caracteres")
        String name,

        @NotBlank(message = "Informe seu nome")
        @Size(max = 100, message = "Seu sobrenome tem que ser menor de 100 caracteres")
        @Size(min = 3, message = "Seu sobrenome tem que ser meior do que 3 caracteres")
        String surname,

        @NotBlank(message = "Informe seu número de telefone")
        @Pattern(regexp = "\\d{13}", message = "Número de telefone inválido")
        String phone,

        @NotNull(message = "Informe sua data de nascimento")
        @Past(message = "Sua data de nascimento deve estar no passado")
        LocalDate birth
) { }
