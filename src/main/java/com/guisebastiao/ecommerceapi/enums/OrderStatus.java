package com.guisebastiao.ecommerceapi.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING_PAYMENT,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELED,
    FAILED;

    public static OrderStatus fromString(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }
}
