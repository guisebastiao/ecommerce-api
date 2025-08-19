package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.auth.ActiveLoginRequest;
import com.guisebastiao.ecommerceapi.dto.request.auth.LoginRequest;
import com.guisebastiao.ecommerceapi.dto.request.auth.RegisterRequest;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;
import com.guisebastiao.ecommerceapi.dto.response.auth.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    DefaultResponse<LoginResponse> login(LoginRequest loginRequest);
    DefaultResponse<ClientSimpleResponse> activeLogin(ActiveLoginRequest activeLoginRequest, HttpServletResponse response);
    DefaultResponse<Void> register(RegisterRequest registerRequest);
    DefaultResponse<Void> activeAccount(String verificationCode);
    DefaultResponse<Void> logout(HttpServletResponse response);
    DefaultResponse<ClientSimpleResponse> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
