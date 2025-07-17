package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.UpdateAccountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.UpdatePasswordRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ClientResponseDTO;

public interface ClientService {
    DefaultDTO<ClientResponseDTO> findById(String id);
    DefaultDTO<Void> updateAccount(UpdateAccountRequestDTO updateAccountRequestDTO);
    DefaultDTO<Void> updatePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO);
    DefaultDTO<Void> deleteAccount();
}
