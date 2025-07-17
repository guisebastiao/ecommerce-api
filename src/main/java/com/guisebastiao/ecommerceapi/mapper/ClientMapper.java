package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.dto.request.RegisterRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ClientResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientPictureMapper.class})
public interface ClientMapper {
    Client toEntity(RegisterRequestDTO registerRequestDTO);
    ClientResponseDTO toDto(Client client);
}
