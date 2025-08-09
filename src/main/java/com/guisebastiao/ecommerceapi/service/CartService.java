package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.request.cart.CartItemRequest;
import com.guisebastiao.ecommerceapi.dto.response.cart.CartItemResponse;

public interface CartService {
    DefaultResponse<Void> addProductToCart(CartItemRequest cartRequest);
    DefaultResponse<PageResponse<CartItemResponse>> findAllCartItems(int offset, int limit);
    DefaultResponse<Void> removeProductFromCart(String cartItemId);
    DefaultResponse<Void> removeAllProductsFromCart(String cartItemId);
}
