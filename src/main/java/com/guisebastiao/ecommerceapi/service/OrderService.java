package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.response.order.OrderResponse;
import com.guisebastiao.ecommerceapi.dto.response.order.PaymentResponse;
import com.guisebastiao.ecommerceapi.dto.request.order.OrderRequest;
import com.guisebastiao.ecommerceapi.dto.request.order.PaymentRequest;

public interface OrderService {
    DefaultResponse<PaymentResponse> createOrder(OrderRequest orderRequest);
    DefaultResponse<PageResponse<OrderResponse>> findAllOrders(int offset, int limit);
    DefaultResponse<Void> payOrder(String orderId, PaymentRequest paymentRequest);
    DefaultResponse<Void> cancelOrder(String orderId);
}
