package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.request.discount.DiscountRequest;
import com.guisebastiao.ecommerceapi.dto.response.discount.DiscountResponse;

public interface DiscountService {
    DefaultResponse<Void> createDiscount(DiscountRequest discountRequest);
    DefaultResponse<PageResponse<DiscountResponse>> findAllDiscounts(int offset, int limit);
    DefaultResponse<Void> updateDiscount(String discountId, DiscountRequest discountRequest);
    DefaultResponse<Void> deleteDiscount(String discountId);

}
