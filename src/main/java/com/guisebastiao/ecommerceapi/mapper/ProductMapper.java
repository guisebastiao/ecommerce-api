package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.request.product.ProductRequest;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;
import com.guisebastiao.ecommerceapi.mapper.resolver.ProductResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductResolver.class, ProductPictureMapper.class})
public interface ProductMapper {
    Product toEntity(ProductRequest productRequestDTO);

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "price", target = "originalPrice")
    @Mapping(target = "price", source = ".", qualifiedByName = "resolveDiscount")
    @Mapping(target = "totalComments", source = ".", qualifiedByName = "resolveTotalComments")
    @Mapping(target = "haveDiscount", source = ".", qualifiedByName = "resolveHaveDiscount")
    @Mapping(target = "reviewRating", source = ".", qualifiedByName = "resolveReviewRating")
    @Mapping(target = "alreadyReviewed", source = ".", qualifiedByName = "resolveAlreadyReviewed")
    @Mapping(target = "alreadyCommented", source = ".", qualifiedByName = "resolveAlreadyCommented")
    @Mapping(target = "isFavorite", source = ".", qualifiedByName = "resolveIsFavorite")
    ProductResponse toDTO(Product product);
}
