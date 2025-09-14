package com.guisebastiao.ecommerceapi.dto.response.order;

import java.util.UUID;

public record PaymentResponse(
        String clientSecret,
        String paymentIntentId,
        UUID orderId
) {}
