package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.request.AddressRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.AddressResponseDTO;

public interface AddressService {
    DefaultDTO<Void> createAddress(AddressRequestDTO addressRequestDTO);
    DefaultDTO<PageResponseDTO<AddressResponseDTO>> findAllAddresses(int offset, int limit);
    DefaultDTO<Void> updateAddress(String addressId, AddressRequestDTO addressRequestDTO);
    DefaultDTO<Void> deleteAddress(String addressId);
}
