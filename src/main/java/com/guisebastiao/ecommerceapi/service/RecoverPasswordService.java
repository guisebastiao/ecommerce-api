package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.recoverPassword.CreateResetPasswordRequest;
import com.guisebastiao.ecommerceapi.dto.request.recoverPassword.ResetPasswordRequest;

public interface RecoverPasswordService {
    DefaultResponse<Void> createRecoverPassword(CreateResetPasswordRequest createResetPasswordRequest);
    DefaultResponse<Void> resetPassword(String code, ResetPasswordRequest resetPasswordRequest);
}
