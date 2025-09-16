package com.guisebastiao.ecommerceapi.dto.response.order;

import com.guisebastiao.ecommerceapi.enums.OrderStatus;
import com.guisebastiao.ecommerceapi.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        String orderNumber,
        OrderStatus orderStatus,
        PaymentMethod paymentMethod,
        BigDecimal total,
        List<OrderItemResponse> items
) { }
