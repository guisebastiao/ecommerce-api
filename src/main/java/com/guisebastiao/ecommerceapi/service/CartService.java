package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.cart.CartItemRequest;
import com.guisebastiao.ecommerceapi.dto.request.cart.UpdateCartItemRequest;
import com.guisebastiao.ecommerceapi.dto.response.cart.CartResponse;

public interface CartService {
    DefaultResponse<Void> addProductToCart(CartItemRequest cartRequest);
    DefaultResponse<CartResponse> findAllCartItems();
    DefaultResponse<Void> updateQuantity(String cartItemId, UpdateCartItemRequest updateCartItemRequest);
    DefaultResponse<Void> removeProductFromCart(String cartItemId);
    DefaultResponse<Void> removeAllProductsFromCart(String cartItemId);
}
