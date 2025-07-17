package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "Informe o nome do produto")
        @Size(max = 250, message = "O nome deve ter no máximo 250 caracteres")
        String name,

        @NotBlank(message = "Informe a descrição do produto")
        String description,

        @NotNull(message = "Informe o preço do produto")
        @DecimalMin(value = "0.00", inclusive = true, message = "O preço deve ser maior ou igual a 0")
        @Digits(integer = 8, fraction = 2, message = "Preço inválido")
        BigDecimal price,

        @NotNull(message = "Informe a quantidade do estoque")
        @Min(value = 0, message = "O estoque não pode ser negativo")
        Integer stock,

        @NotBlank(message = "Informe a categoria do produto")
        String categoryId
) { }
