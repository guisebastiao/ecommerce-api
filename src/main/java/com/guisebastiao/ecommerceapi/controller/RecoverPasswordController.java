package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.recoverPassword.CreateResetPasswordRequest;
import com.guisebastiao.ecommerceapi.dto.request.recoverPassword.ResetPasswordRequest;
import com.guisebastiao.ecommerceapi.service.RecoverPasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recover-password")
public class RecoverPasswordController {

    @Autowired
    private RecoverPasswordService recoverPasswordService;

    @PostMapping
    public ResponseEntity<DefaultResponse<Void>> createRecoverPassword(@RequestBody @Valid CreateResetPasswordRequest createResetPasswordRequest) {
        DefaultResponse<Void> response = this.recoverPasswordService.createRecoverPassword(createResetPasswordRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    public ResponseEntity<DefaultResponse<Void>> resetPassword(@PathVariable String code, @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        DefaultResponse<Void> response = this.recoverPasswordService.resetPassword(code, resetPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
