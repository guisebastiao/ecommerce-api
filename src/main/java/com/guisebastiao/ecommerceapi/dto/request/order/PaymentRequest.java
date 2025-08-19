package com.guisebastiao.ecommerceapi.dto.request.order;

import jakarta.validation.constraints.NotBlank;

public record PaymentRequest(
        @NotBlank(message = "Informe o stripe payment id")
        String stripePaymentId
) { }
