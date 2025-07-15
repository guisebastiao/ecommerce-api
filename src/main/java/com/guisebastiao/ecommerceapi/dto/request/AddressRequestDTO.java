package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO(
        @NotBlank(message = "Informe a rua")
        @Size(max = 100, message = "A rua tem que possuir menos de 100 caracteres")
        String street,

        @NotBlank(message = "Informe o número")
        @Size(max = 20, message = "O número deve ter no máximo 20 caracteres")
        String number,

        @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
        String complement,

        @NotBlank(message = "Informe o bairro")
        @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
        String neighborhood,

        @NotBlank(message = "Informe a cidade")
        @Size(max = 60, message = "A cidade deve ter no máximo 100 caracteres")
        String city,

        @NotBlank(message = "Informe a estado")
        @Size(max = 100, message = "O estado deve ter no máximo 100 caracteres")
        String state,

        @NotBlank(message = "Informe o CEP")
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido")
        String zip,

        @NotBlank(message = "Informe o país")
        @Size(max = 100, message = "O país deve ter no máximo 100 caracteres")
        String country
){ }
