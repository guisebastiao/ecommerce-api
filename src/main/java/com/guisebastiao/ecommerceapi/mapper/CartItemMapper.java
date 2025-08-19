package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.CartItem;
import com.guisebastiao.ecommerceapi.dto.request.cart.CartItemRequest;
import com.guisebastiao.ecommerceapi.dto.response.cart.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartItemMapper {
    CartItem toEntity(CartItemRequest cartItemRequestDTO);

    @Mapping(source = "id", target = "cartItemId")
    CartItemResponse toDTO(CartItem cartItem);
}
