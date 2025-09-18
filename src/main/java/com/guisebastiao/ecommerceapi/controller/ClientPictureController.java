package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.clientPicture.ClientPictureRequest;
import com.guisebastiao.ecommerceapi.dto.response.clientPicture.ClientPictureResponse;
import com.guisebastiao.ecommerceapi.service.ClientPictureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client-picture")
public class ClientPictureController {

    @Autowired
    private ClientPictureService clientPictureService;

    @PostMapping
    public ResponseEntity<DefaultResponse<ClientPictureResponse>> createClientPicture(@ModelAttribute @Valid ClientPictureRequest clientPictureRequest) {
        DefaultResponse<ClientPictureResponse> response = this.clientPictureService.createClientPicture(clientPictureRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<DefaultResponse<ClientPictureResponse>> getClientPicture(@PathVariable String clientId) {
        DefaultResponse<ClientPictureResponse> response = this.clientPictureService.getClientPicture(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<DefaultResponse<Void>> deleteClientPicture() {
        DefaultResponse<Void> response = this.clientPictureService.deleteClientPicture();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
