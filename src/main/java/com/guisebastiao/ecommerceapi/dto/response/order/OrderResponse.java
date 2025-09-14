package com.guisebastiao.ecommerceapi.dto.response.order;

import com.guisebastiao.ecommerceapi.domain.OrderItem;
import com.guisebastiao.ecommerceapi.enums.OrderStatus;
import com.guisebastiao.ecommerceapi.enums.PaymentMethod;

import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        String orderNumber,
        OrderStatus orderStatus,
        PaymentMethod paymentMethod,
        List<OrderItemResponse> items
) { }
