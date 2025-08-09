package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.request.category.CategoryRequest;
import com.guisebastiao.ecommerceapi.dto.response.category.CategoryResponse;

public interface CategoryService {
    DefaultResponse<Void> createCategory(CategoryRequest categoryRequest);
    DefaultResponse<PageResponse<CategoryResponse>> findAllCategories(int offset, int limit);
    DefaultResponse<Void> updateCategory(String categoryId, CategoryRequest categoryRequest);
    DefaultResponse<Void> deleteCategory(String categoryId);
}
