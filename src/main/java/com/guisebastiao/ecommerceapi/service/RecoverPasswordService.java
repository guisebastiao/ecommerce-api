package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.CreateResetPasswordRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.ResetPasswordRequestDTO;

public interface RecoverPasswordService {
    DefaultDTO<Void> createRecoverPassword(CreateResetPasswordRequestDTO createResetPasswordRequestDTO);
    DefaultDTO<Void> resetPassword(String code, ResetPasswordRequestDTO resetPasswordRequestDTO);
}
