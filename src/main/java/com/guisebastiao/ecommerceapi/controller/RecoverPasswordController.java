package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.CreateResetPasswordRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.ResetPasswordRequestDTO;
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
    public ResponseEntity<DefaultDTO<Void>> createRecoverPassword(@RequestBody @Valid CreateResetPasswordRequestDTO createResetPasswordRequestDTO) {
        DefaultDTO<Void> response = this.recoverPasswordService.createRecoverPassword(createResetPasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    public ResponseEntity<DefaultDTO<Void>> resetPassword(@PathVariable String code, @RequestBody @Valid ResetPasswordRequestDTO resetPasswordRequestDTO) {
        DefaultDTO<Void> response = this.recoverPasswordService.resetPassword(code, resetPasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
