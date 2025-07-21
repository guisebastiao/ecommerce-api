package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FavoriteRequestDTO(
        @NotBlank(message = "Informe o ID do produto")
        String productId
) { }
