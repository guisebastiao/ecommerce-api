package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Discount;
import com.guisebastiao.ecommerceapi.dto.request.DiscountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.DiscountResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    Discount toEntity(DiscountRequestDTO discountRequestDTO);
    DiscountResponseDTO toDto(Discount discount);
}
