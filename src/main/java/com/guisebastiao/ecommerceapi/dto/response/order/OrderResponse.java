package com.guisebastiao.ecommerceapi.dto.response.order;

import java.util.UUID;

public record OrderResponse(
        String clientSecret,
        String paymentIntentId,
        UUID orderId
) {}
