package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.request.CartItemRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CartItemResponseDTO;

public interface CartService {
    DefaultDTO<Void> addProductToCart(CartItemRequestDTO cartRequestDTO);
    DefaultDTO<PageResponseDTO<CartItemResponseDTO>> findAllCartItems(int offset, int limit);
    DefaultDTO<Void> removeProductFromCart(String cartItemId);
    DefaultDTO<Void> removeAllProductsFromCart(String cartItemId);
}
