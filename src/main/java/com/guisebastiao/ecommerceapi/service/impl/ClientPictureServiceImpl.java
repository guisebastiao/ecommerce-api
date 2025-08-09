package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.config.MinioConfig;
import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.ClientPicture;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.clientPicture.ClientPictureRequest;
import com.guisebastiao.ecommerceapi.dto.response.clientPicture.ClientPictureResponse;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.FailedUploadFileException;
import com.guisebastiao.ecommerceapi.repository.ClientPictureRepository;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import com.guisebastiao.ecommerceapi.service.ClientPictureService;
import com.guisebastiao.ecommerceapi.util.CodeGenerator;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class ClientPictureServiceImpl implements ClientPictureService {

    @Autowired
    private ClientPictureRepository clientPictureRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private AuthProvider clientAuthProvider;

    @Override
    @Transactional
    public DefaultResponse<Void> createClientPicture(ClientPictureRequest clientPictureRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        if(client.getClientPicture() != null) {
            try {
                this.minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioConfig.getMinioBucket())
                                .object(client.getClientPicture().getObjectId())
                                .build()
                );
            } catch (Exception e) {
                throw new FailedUploadFileException("Ocorreu um erro inesperado ao enviar sua imagem de perfil", e);
            }

            client.setClientPicture(null);
            this.clientRepository.save(client);
        }

        MultipartFile file = clientPictureRequest.file();
        String objectId = this.codeGenerator.generateToken();
        String contentType = file.getContentType();

        ClientPicture clientPicture = new ClientPicture();
        clientPicture.setClient(client);
        clientPicture.setObjectId(objectId);

        this.clientPictureRepository.save(clientPicture);

        try {
            InputStream inputStream = file.getInputStream();

            this.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getMinioBucket())
                            .object(minioConfig.getClientPicturesFolder() + objectId)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new FailedUploadFileException("Ocorreu um erro inesperado ao enviar sua imagem de perfil", e);
        }

        return new DefaultResponse<Void>(true, "Imagem de perfil foi salva com sucesso", null);
    }

    @Override
    public DefaultResponse<ClientPictureResponse> getClientPicture(String clientId) {
        Client client = this.clientRepository.findById(UUIDConverter.toUUID(clientId))
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));

        ClientPicture picture = client.getClientPicture();

        try {
            if(picture != null) {
                String presignedUrl = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(minioConfig.getMinioBucket())
                                .object(minioConfig.getClientPicturesFolder() + picture.getObjectId())
                                .expiry(604800)
                                .build()
                );

                ClientPictureResponse data = new ClientPictureResponse(picture.getId(), picture.getObjectId(), presignedUrl);
                return new DefaultResponse<ClientPictureResponse>(true, "Imagem perfil encontrada com sucesso", data);
            }

            return new DefaultResponse<ClientPictureResponse>(true, "O cliente não possuí foto de perfil", null);
        } catch (Exception e) {
            throw new FailedUploadFileException("Ocorreu um erro inesperado ao buscar a imagem de perfil", e);
        }
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteClientPicture() {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        if(client.getClientPicture() != null) {
            try {
                this.minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioConfig.getMinioBucket())
                                .object(minioConfig.getClientPicturesFolder() + client.getClientPicture().getObjectId())
                                .build()
                );
            } catch (Exception e) {
                throw new FailedUploadFileException("Ocorreu um erro inesperado ao enviar sua imagem de perfil", e);
            }

            client.setClientPicture(null);
            this.clientRepository.save(client);
        }

        return new DefaultResponse<Void>(true, "Imagem perfil excluida com sucesso", null);
    }
}
