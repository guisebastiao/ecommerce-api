package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.category.CategoryRequest;
import com.guisebastiao.ecommerceapi.dto.response.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    DefaultResponse<Void> createCategory(CategoryRequest categoryRequest);
    DefaultResponse<List<CategoryResponse>> findAllCategories();
    DefaultResponse<Void> updateCategory(String categoryId, CategoryRequest categoryRequest);
    DefaultResponse<Void> deleteCategory(String categoryId);
}
