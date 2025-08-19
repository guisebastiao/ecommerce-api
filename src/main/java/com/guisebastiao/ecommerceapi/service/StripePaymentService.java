package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.enums.PaymentStatus;

import java.math.BigDecimal;

public interface StripePaymentService {
    String createPaymentIntent(BigDecimal amount);
    PaymentStatus confirmPayment(String paymentIntentId, String paymentMethodId);
    String getClientSecret(String paymentIntentId);
    void cancelPaymentIntent(String paymentIntentId);
}
