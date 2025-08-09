package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.ClientPicture;
import com.guisebastiao.ecommerceapi.dto.response.clientPicture.ClientPictureResponse;
import com.guisebastiao.ecommerceapi.mapper.resolver.ClientPictureResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientPictureResolver.class})
public interface ClientPictureMapper {
    @Mapping(source = "id", target = "clientPictureId")
    @Mapping(target = "url", source = ".", qualifiedByName = "resolvePictureUrl")
    ClientPictureResponse toDTO(ClientPicture clientPicture);
}
