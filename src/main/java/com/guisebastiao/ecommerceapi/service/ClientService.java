package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.client.UpdateAccountRequest;
import com.guisebastiao.ecommerceapi.dto.request.client.UpdatePasswordRequest;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientResponse;

public interface ClientService {
    DefaultResponse<ClientResponse> findById(String id);
    DefaultResponse<Void> updateAccount(UpdateAccountRequest updateAccountRequest);
    DefaultResponse<Void> updatePassword(UpdatePasswordRequest updatePasswordRequest);
    DefaultResponse<Void> deleteAccount();
}
