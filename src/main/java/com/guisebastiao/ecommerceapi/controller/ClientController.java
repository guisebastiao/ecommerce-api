package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.client.UpdateAccountRequest;
import com.guisebastiao.ecommerceapi.dto.request.client.UpdatePasswordRequest;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientResponse;
import com.guisebastiao.ecommerceapi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ResponseEntity<DefaultResponse<ClientResponse>> findClientById(@PathVariable String clientId){
        DefaultResponse<ClientResponse> response = this.clientService.findById(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<DefaultResponse<Void>> updateAccount(@RequestBody @Valid UpdateAccountRequest updateAccountRequest) {
        DefaultResponse<Void> response = this.clientService.updateAccount(updateAccountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update-password")
    public ResponseEntity<DefaultResponse<Void>> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest) {
        DefaultResponse<Void> response = this.clientService.updatePassword(updatePasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<DefaultResponse<Void>> deleteAccount() {
        DefaultResponse<Void> response = this.clientService.deleteAccount();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
