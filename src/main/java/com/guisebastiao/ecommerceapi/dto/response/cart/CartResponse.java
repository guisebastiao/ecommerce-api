package com.guisebastiao.ecommerceapi.dto.response.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        List<CartItemResponse> cartItems,
        BigDecimal total,
        BigDecimal subtotal,
        BigDecimal totalDiscounts
) { }
