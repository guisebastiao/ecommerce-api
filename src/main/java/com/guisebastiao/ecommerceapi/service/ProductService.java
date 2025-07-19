package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ApplyDiscountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.ProductRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ProductResponseDTO;

public interface ProductService {
    DefaultDTO<Void> createProduct(ProductRequestDTO productRequestDTO);
    DefaultDTO<Void> applyDiscount(ApplyDiscountRequestDTO applyDiscountRequestDTO);
    DefaultDTO<ProductResponseDTO> findProductById(String productId);
    DefaultDTO<Void> updateProduct(String productId, ProductRequestDTO productRequestDTO);
    DefaultDTO<Void> deleteProduct(String productId);
}
