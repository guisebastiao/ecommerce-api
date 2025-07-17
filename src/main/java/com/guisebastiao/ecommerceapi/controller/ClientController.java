package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.UpdateAccountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.UpdatePasswordRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ClientResponseDTO;
import com.guisebastiao.ecommerceapi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ResponseEntity<DefaultDTO<ClientResponseDTO>> findClientById(@PathVariable String clientId){
        DefaultDTO<ClientResponseDTO> response = this.clientService.findById(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<DefaultDTO<Void>> updateAccount(@RequestBody @Valid UpdateAccountRequestDTO updateAccountRequestDTO) {
        DefaultDTO<Void> response = this.clientService.updateAccount(updateAccountRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update-password")
    public ResponseEntity<DefaultDTO<Void>> updatePassword(@RequestBody @Valid UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        DefaultDTO<Void> response = this.clientService.updatePassword(updatePasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<DefaultDTO<Void>> deleteAccount() {
        DefaultDTO<Void> response = this.clientService.deleteAccount();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
