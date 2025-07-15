package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ClientPictureRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ClientPictureResponseDTO;

public interface ClientPictureService {
    DefaultDTO<Void> createClientPicture(ClientPictureRequestDTO clientPictureRequestDTO);
    DefaultDTO<ClientPictureResponseDTO> getClientPicture(String clientId);
    DefaultDTO<Void> deleteClientPicture();
}
