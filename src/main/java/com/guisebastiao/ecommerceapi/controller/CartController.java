package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.cart.CartItemRequest;
import com.guisebastiao.ecommerceapi.dto.response.cart.CartItemResponse;
import com.guisebastiao.ecommerceapi.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<DefaultResponse<Void>> addProductToCart(@RequestBody @Valid CartItemRequest cartItemRequest) {
        DefaultResponse<Void> response = this.cartService.addProductToCart(cartItemRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<PageResponse<CartItemResponse>>> findAllCartItems(@Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<CartItemResponse>> response = this.cartService.findAllCartItems(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<DefaultResponse<Void>> removeProductFromCart(@PathVariable String cartItemId) {
        DefaultResponse<Void> response = this.cartService.removeProductFromCart(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{cartItemId}/remove-all")
    public ResponseEntity<DefaultResponse<Void>> removeAllProductsFromCart(@PathVariable String cartItemId) {
        DefaultResponse<Void> response = this.cartService.removeAllProductsFromCart(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
