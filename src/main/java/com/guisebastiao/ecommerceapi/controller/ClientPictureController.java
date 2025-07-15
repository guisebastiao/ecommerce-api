package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ClientPictureRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ClientPictureResponseDTO;
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
    public ResponseEntity<DefaultDTO<Void>> createClientPicture(@ModelAttribute @Valid ClientPictureRequestDTO clientPictureRequestDTO) {
        DefaultDTO<Void> response = this.clientPictureService.createClientPicture(clientPictureRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<DefaultDTO<ClientPictureResponseDTO>> getClientPicture(@PathVariable String clientId) {
        DefaultDTO<ClientPictureResponseDTO> response = this.clientPictureService.getClientPicture(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<DefaultDTO<Void>> deleteClientPicture() {
        DefaultDTO<Void> response = this.clientPictureService.deleteClientPicture();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
