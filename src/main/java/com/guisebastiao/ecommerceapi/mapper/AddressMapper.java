package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Address;
import com.guisebastiao.ecommerceapi.dto.request.address.AddressRequest;
import com.guisebastiao.ecommerceapi.dto.response.address.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressRequest addressRequestDTO);

    @Mapping(source = "id", target = "addressId")
    AddressResponse toDto(Address address);
}
