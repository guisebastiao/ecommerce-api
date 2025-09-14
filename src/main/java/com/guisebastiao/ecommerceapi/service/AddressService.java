package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.request.address.AddressRequest;
import com.guisebastiao.ecommerceapi.dto.response.address.AddressResponse;

import java.util.List;

public interface AddressService {
    DefaultResponse<Void> createAddress(AddressRequest addressRequest);
    DefaultResponse<List<AddressResponse>> findAllAddresses();
    DefaultResponse<Void> updateAddress(String addressId, AddressRequest addressRequest);
    DefaultResponse<Void> deleteAddress(String addressId);
}
