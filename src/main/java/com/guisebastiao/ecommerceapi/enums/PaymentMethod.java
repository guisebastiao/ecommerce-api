package com.guisebastiao.ecommerceapi.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CARD;

    public static PaymentMethod fromString(String value) {
        return PaymentMethod.valueOf(value.toUpperCase());
    }
}