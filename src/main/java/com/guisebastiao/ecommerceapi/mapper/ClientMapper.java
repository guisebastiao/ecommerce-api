package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.dto.request.auth.RegisterRequest;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientResponse;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientPictureMapper.class})
public interface ClientMapper {
    Client toEntity(RegisterRequest registerRequestDTO);

    @Mapping(source = "id", target = "clientId")
    ClientResponse toDTO(Client client);

    @Mapping(source = "id", target = "clientId")
    ClientSimpleResponse toSimpleDTO(Client client);
}
