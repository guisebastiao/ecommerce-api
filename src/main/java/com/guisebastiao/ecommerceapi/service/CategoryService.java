package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.request.CategoryRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CategoryResponseDTO;

public interface CategoryService {
    DefaultDTO<Void> createCategory(CategoryRequestDTO categoryRequestDTO);
    DefaultDTO<PageResponseDTO<CategoryResponseDTO>> findAllCategories(int offset, int limit);
    DefaultDTO<Void> updateCategory(String categoryId, CategoryRequestDTO categoryRequestDTO);
    DefaultDTO<Void> deleteCategory(String categoryId);
}
