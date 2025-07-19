package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.request.DiscountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.DiscountResponseDTO;

public interface DiscountService {
    DefaultDTO<Void> createDiscount(DiscountRequestDTO discountRequestDTO);
    DefaultDTO<PageResponseDTO<DiscountResponseDTO>> findAllDiscounts(int offset, int limit);
    DefaultDTO<Void> updateDiscount(String discountId, DiscountRequestDTO discountRequestDTO);
    DefaultDTO<Void> deleteDiscount(String discountId);

}
