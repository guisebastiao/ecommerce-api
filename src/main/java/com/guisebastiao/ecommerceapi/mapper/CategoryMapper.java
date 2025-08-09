package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Category;
import com.guisebastiao.ecommerceapi.dto.request.category.CategoryRequest;
import com.guisebastiao.ecommerceapi.dto.response.category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest categoryRequestDTO);

    @Mapping(source = "id", target = "categoryId")
    CategoryResponse toDTO(Category category);
}
