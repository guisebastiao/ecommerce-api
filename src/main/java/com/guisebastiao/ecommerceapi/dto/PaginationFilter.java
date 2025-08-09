package com.guisebastiao.ecommerceapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PaginationFilter(
        @NotNull(message = "O número da página é obrigatório")
        @Min(value = 0, message = "O número da página deve ser zero ou maior")
        Integer offset,

        @NotNull(message = "A quantidade de itens é obrigatória")
        @Min(value = 1, message = "A quantidade mínima de itens é 1")
        @Max(value = 50, message = "A quantidade máxima de itens é 50")
        Integer limit
) { }
