package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Address;
import com.guisebastiao.ecommerceapi.dto.request.AddressRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.AddressResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressRequestDTO addressRequestDTO);
    AddressResponseDTO toDto(Address address);
}
