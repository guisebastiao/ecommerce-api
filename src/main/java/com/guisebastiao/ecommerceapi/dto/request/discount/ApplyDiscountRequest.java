package com.guisebastiao.ecommerceapi.dto.request.discount;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

public record ApplyDiscountRequest(
        @NotBlank(message = "Informe o ID do produto")
        @UUID(message = "O ID do produto está inválido")
        String productId,

        @NotBlank(message = "Informe o ID do desconto")
        @UUID(message = "O ID do desconto está inválido")
        String discountId
) {}
