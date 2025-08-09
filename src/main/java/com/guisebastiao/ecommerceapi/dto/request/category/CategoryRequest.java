package com.guisebastiao.ecommerceapi.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Informe o nome da categoria")
        @Size(max = 50, message = "A categoria tem que ser menor de 50 caracteres")
        String name
) { }
