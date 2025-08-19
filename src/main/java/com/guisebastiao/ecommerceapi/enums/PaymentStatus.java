package com.guisebastiao.ecommerceapi.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCEEDED,
    REQUIRES_ACTION,
    PROCESSING,
    FAILED;

    public static PaymentStatus fromString(String value) {
        return PaymentStatus.valueOf(value.toUpperCase());
    }
}
