package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.auth.ActiveLoginRequest;
import com.guisebastiao.ecommerceapi.dto.request.auth.LoginRequest;
import com.guisebastiao.ecommerceapi.dto.request.auth.RefreshTokenRequest;
import com.guisebastiao.ecommerceapi.dto.request.auth.RegisterRequest;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;
import com.guisebastiao.ecommerceapi.dto.response.auth.LoginResponse;
import com.guisebastiao.ecommerceapi.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        DefaultResponse<LoginResponse> response = this.authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/active-login")
    public ResponseEntity<DefaultResponse<ClientSimpleResponse>> activeLogin(@RequestBody @Valid ActiveLoginRequest activeLoginRequest, HttpServletResponse httpResponse) {
        DefaultResponse<ClientSimpleResponse> response = this.authService.activeLogin(activeLoginRequest, httpResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse<Void>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        DefaultResponse<Void> response = this.authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/active-account/{verificationCode}")
    public ResponseEntity<DefaultResponse<Void>> activeAccount(@PathVariable String verificationCode) {
        DefaultResponse<Void> response = this.authService.activeAccount(verificationCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<DefaultResponse<Void>> logout(HttpServletResponse httpResponse) {
        DefaultResponse<Void> response = this.authService.logout(httpResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<DefaultResponse<ClientSimpleResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest, HttpServletResponse httpResponse) {
        DefaultResponse<ClientSimpleResponse> response = this.authService.refreshToken(refreshTokenRequest, httpResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
