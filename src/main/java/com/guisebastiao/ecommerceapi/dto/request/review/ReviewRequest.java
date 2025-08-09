package com.guisebastiao.ecommerceapi.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull(message = "Informe o valor da avaliação")
        @Min(value = 1, message = "O valor mínimo da avaliação é 1")
        @Max(value = 5, message = "O valor máximo da avaliação é 5")
        Integer rating
) { }
