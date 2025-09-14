package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.OrderItem;
import com.guisebastiao.ecommerceapi.dto.response.order.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    @Mapping(source = "id", target = "orderItemId")
    OrderItemResponse toDTO(OrderItem orderItem);
}
