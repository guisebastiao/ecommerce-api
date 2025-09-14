package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.response.order.OrderResponse;
import com.guisebastiao.ecommerceapi.dto.response.order.PaymentResponse;
import com.guisebastiao.ecommerceapi.dto.request.order.OrderRequest;
import com.guisebastiao.ecommerceapi.dto.request.order.PaymentRequest;
import com.guisebastiao.ecommerceapi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-payment")
    public ResponseEntity<DefaultResponse<PaymentResponse>> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        DefaultResponse<PaymentResponse> response = this.orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<PageResponse<OrderResponse>>> findAllOrders(@Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<OrderResponse>> response = this.orderService.findAllOrders(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<DefaultResponse<Void>> payOrder(@PathVariable String orderId, @RequestBody PaymentRequest paymentRequest) {
        DefaultResponse<Void> response = this.orderService.payOrder(orderId, paymentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<DefaultResponse<Void>> cancelOrder(@PathVariable String orderId) {
        DefaultResponse<Void> response = this.orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
