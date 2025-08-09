package com.guisebastiao.ecommerceapi.dto.request.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequest(
        @NotBlank(message = "Informe o ID do produto")
        String productId,

        @NotNull(message = "Informe a quantidade")
        @Positive(message = "A quantidade tem que ser positiva")
        Integer quantity
) { }
