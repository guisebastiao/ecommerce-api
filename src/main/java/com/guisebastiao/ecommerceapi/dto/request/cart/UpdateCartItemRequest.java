package com.guisebastiao.ecommerceapi.dto.request.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCartItemRequest(
        @NotNull(message = "Informe a quantidade")
        @PositiveOrZero(message = "A quantidade tem que ser positiva e não pode ser zero")
        Integer quantity
) { }
