package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.enums.PaymentStatus;
import com.guisebastiao.ecommerceapi.exception.BadGatewayException;
import com.guisebastiao.ecommerceapi.service.StripePaymentService;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentServiceImpl implements StripePaymentService {

    @Override
    public String createPaymentIntent(BigDecimal amount) {
        try {
            long amountInCents = amount.multiply(BigDecimal.valueOf(100)).longValueExact();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("brl")
                    .addPaymentMethodType("card")
                    .putMetadata("test_mode", "true")
                    .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getId();
        } catch (Exception e) {
            throw new BadGatewayException("Erro ao criar o pagamento", e);
        }
    }

    @Override
    public PaymentStatus confirmPayment(String paymentIntentId, String paymentMethodId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            if ("succeeded".equals(paymentIntent.getStatus())) {
                return PaymentStatus.SUCCEEDED;
            }

            PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
                    .setPaymentMethod(paymentMethodId)
                    .build();

            paymentIntent = paymentIntent.confirm(confirmParams);

            PaymentStatus status = mapStripeStatusToPaymentStatus(paymentIntent.getStatus());

            return status;
        } catch (Exception e) {
            throw new BadGatewayException("Erro ao confirmar pagamento", e);
        }
    }

    @Override
    public String getClientSecret(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return paymentIntent.getClientSecret();
        } catch (Exception e) {
            throw new BadGatewayException("Erro ao recuperar dados de pagamento", e);
        }
    }

    @Override
    public void cancelPaymentIntent(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            if (canBeCanceled(paymentIntent.getStatus())) {
                paymentIntent.cancel();
            }

        } catch (Exception e) {
            throw new BadGatewayException("Erro ao cancelar PaymentIntent", e);
        }
    }

    private boolean canBeCanceled(String status) {
        return "requires_payment_method".equals(status) || "requires_confirmation".equals(status) || "requires_action".equals(status);
    }

    private PaymentStatus mapStripeStatusToPaymentStatus(String stripeStatus) {
        return switch (stripeStatus) {
            case "succeeded" -> PaymentStatus.SUCCEEDED;
            case "requires_action", "requires_source_action" -> PaymentStatus.REQUIRES_ACTION;
            case "processing" -> PaymentStatus.PROCESSING;
            case "canceled" -> PaymentStatus.FAILED;
            default -> {
                yield PaymentStatus.FAILED;
            }
        };
    }
}
