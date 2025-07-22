package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.CartItem;
import com.guisebastiao.ecommerceapi.dto.request.CartItemRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CartItemResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItemRequestDTO cartItemRequestDTO);
    CartItemResponseDTO toDto(CartItem cartItem);
}
