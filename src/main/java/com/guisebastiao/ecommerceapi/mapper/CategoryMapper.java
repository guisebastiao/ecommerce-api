package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Category;
import com.guisebastiao.ecommerceapi.dto.request.CategoryRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CategoryResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO toDto(Category category);
}
