package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "Informe o nome da categoria")
        @Size(max = 50, message = "A categoria tem que ser menor de 50 caracteres")
        String name
) { }
