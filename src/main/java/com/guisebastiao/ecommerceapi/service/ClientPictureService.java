package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.clientPicture.ClientPictureRequest;
import com.guisebastiao.ecommerceapi.dto.response.clientPicture.ClientPictureResponse;

public interface ClientPictureService {
    DefaultResponse<ClientPictureResponse> createClientPicture(ClientPictureRequest clientPictureRequest);
    DefaultResponse<ClientPictureResponse> getClientPicture(String clientId);
    DefaultResponse<Void> deleteClientPicture();
}
