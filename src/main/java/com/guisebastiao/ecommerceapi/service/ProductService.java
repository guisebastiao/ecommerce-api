package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.discount.ApplyDiscountRequest;
import com.guisebastiao.ecommerceapi.dto.request.product.ProductRequest;
import com.guisebastiao.ecommerceapi.dto.response.discount.DiscountResponse;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;

public interface ProductService {
    DefaultResponse<Void> createProduct(ProductRequest productRequest);
    DefaultResponse<Void> applyDiscount(ApplyDiscountRequest applyDiscountRequest);
    DefaultResponse<ProductResponse> findProductById(String productId);
    DefaultResponse<PageResponse<ProductResponse>> findAllProducts(String search, String category, PaginationFilter pagination);
    DefaultResponse<Void> updateProduct(String productId, ProductRequest productRequest);
    DefaultResponse<Void> deleteProduct(String productId);
    DefaultResponse<Void> removeDiscount(String productId);
}
