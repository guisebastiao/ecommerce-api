package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ActiveLoginRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.LoginRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.RefreshTokenRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.RegisterRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ActiveLoginResponseDTO;
import com.guisebastiao.ecommerceapi.dto.response.LoginResponseDTO;
import com.guisebastiao.ecommerceapi.dto.response.RefreshTokenResponseDTO;

public interface AuthService {
    DefaultDTO<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);
    DefaultDTO<ActiveLoginResponseDTO> activeLogin(ActiveLoginRequestDTO activeLoginRequestDTO);
    DefaultDTO<Void> register(RegisterRequestDTO registerRequestDTO);
    DefaultDTO<Void> activeAccount(String verificationCode);
    DefaultDTO<RefreshTokenResponseDTO> refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}
