package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.request.ProductRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ProductResponseDTO;
import com.guisebastiao.ecommerceapi.mapper.resolver.ProductResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductResolver.class, ProductPictureMapper.class})
public interface ProductMapper {
    Product toEntity(ProductRequestDTO productRequestDTO);

    @Mapping(source = "price", target = "originalPrice")
    @Mapping(target = "price", source = ".", qualifiedByName = "resolveDiscount")
    @Mapping(target = "haveDiscount", source = ".", qualifiedByName = "resolveHaveDiscount")
    @Mapping(target = "reviewRating", source = ".", qualifiedByName = "resolveReviewRating")
    ProductResponseDTO toDto(Product product);
}
