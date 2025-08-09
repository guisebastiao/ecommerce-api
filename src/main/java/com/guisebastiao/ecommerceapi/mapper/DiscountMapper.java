package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Discount;
import com.guisebastiao.ecommerceapi.dto.request.discount.DiscountRequest;
import com.guisebastiao.ecommerceapi.dto.response.discount.DiscountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    Discount toEntity(DiscountRequest discountRequestDTO);

    @Mapping(source = "id", target = "discountId")
    DiscountResponse toDTO(Discount discount);
}
