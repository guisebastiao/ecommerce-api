package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ActiveLoginRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.LoginRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.RefreshTokenRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.RegisterRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ActiveLoginResponseDTO;
import com.guisebastiao.ecommerceapi.dto.response.LoginResponseDTO;
import com.guisebastiao.ecommerceapi.dto.response.RefreshTokenResponseDTO;
import com.guisebastiao.ecommerceapi.service.AuthService;
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
    public ResponseEntity<DefaultDTO<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        DefaultDTO<LoginResponseDTO> response = this.authService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/active-login")
    public ResponseEntity<DefaultDTO<ActiveLoginResponseDTO>> activeLogin(@RequestBody @Valid ActiveLoginRequestDTO activeLoginRequestDTO) {
        DefaultDTO<ActiveLoginResponseDTO> response = this.authService.activeLogin(activeLoginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<DefaultDTO<Void>> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        DefaultDTO<Void> response = this.authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/active-account/{verificationCode}")
    public ResponseEntity<DefaultDTO<Void>> activeAccount(@PathVariable String verificationCode) {
        DefaultDTO<Void> response = this.authService.activeAccount(verificationCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<DefaultDTO<RefreshTokenResponseDTO>> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO) {
        DefaultDTO<RefreshTokenResponseDTO> response = this.authService.refreshToken(refreshTokenRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
