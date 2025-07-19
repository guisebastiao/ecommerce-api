package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.ProductPicture;
import com.guisebastiao.ecommerceapi.dto.response.ProductPictureResponseDTO;
import com.guisebastiao.ecommerceapi.mapper.resolver.ProductPictureResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductPictureResolver.class})
public interface ProductPictureMapper {
    @Mapping(target = "url", source = ".", qualifiedByName = "resolvePictureUrl")
    ProductPictureResponseDTO toDto(ProductPicture productPicture);
}
