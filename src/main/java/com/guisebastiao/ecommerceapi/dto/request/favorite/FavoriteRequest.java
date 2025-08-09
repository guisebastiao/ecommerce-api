package com.guisebastiao.ecommerceapi.dto.request.favorite;

import jakarta.validation.constraints.NotBlank;

public record FavoriteRequest(
        @NotBlank(message = "Informe o ID do produto")
        String productId
) { }
