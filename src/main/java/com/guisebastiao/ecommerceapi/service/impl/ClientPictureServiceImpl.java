package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.ClientPicture;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ClientPictureRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ClientPictureResponseDTO;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.FailedUploadFileException;
import com.guisebastiao.ecommerceapi.mapper.ClientPictureMapper;
import com.guisebastiao.ecommerceapi.repository.ClientPictureRepository;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
import com.guisebastiao.ecommerceapi.service.ClientPictureService;
import com.guisebastiao.ecommerceapi.util.CodeGenerator;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static com.guisebastiao.ecommerceapi.config.MinioConfig.BUCKET_CLIENT_PICTURES;

@Service
public class ClientPictureServiceImpl implements ClientPictureService {

    @Autowired
    private ClientPictureRepository clientPictureRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private ClientAuthProvider clientAuthProvider;

    @Autowired
    private ClientPictureMapper clientPictureMapper;

    @Override
    public DefaultDTO<Void> createClientPicture(ClientPictureRequestDTO clientPictureRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        if(client.getClientPicture() != null) {
            try {
                this.minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(BUCKET_CLIENT_PICTURES)
                                .object(client.getClientPicture().getObjectId())
                                .build()
                );
            } catch (Exception e) {
                throw new FailedUploadFileException("Ocorreu um erro inesperado ao enviar sua imagem de perfil");
            }

            client.setClientPicture(null);
            this.clientRepository.save(client);
        }

        MultipartFile file = clientPictureRequestDTO.file();
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
                            .bucket(BUCKET_CLIENT_PICTURES)
                            .object(objectId)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new FailedUploadFileException("Ocorreu um erro inesperado ao enviar sua imagem de perfil");
        }

        return new DefaultDTO<Void>(Boolean.TRUE, "Imagem de perfil foi salva com sucesso", null);
    }

    @Override
    public DefaultDTO<ClientPictureResponseDTO> getClientPicture(String clientId) {
        Client client = this.clientRepository.findById(UUIDConverter.toUUID(clientId))
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));

        ClientPicture picture = client.getClientPicture();

        try {
            if(picture != null) {
                String presignedUrl = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(BUCKET_CLIENT_PICTURES)
                                .object(picture.getObjectId())
                                .expiry(604800)
                                .build()
                );

                ClientPictureResponseDTO data = new ClientPictureResponseDTO(picture.getId(), picture.getObjectId(), presignedUrl);
                return new DefaultDTO<ClientPictureResponseDTO>(Boolean.TRUE, "Imagem perfil encontrada com sucesso", data);
            }

            return new DefaultDTO<ClientPictureResponseDTO>(Boolean.TRUE, "O cliente não possuí foto de perfil", null);
        } catch (Exception e) {
            throw new FailedUploadFileException("Ocorreu um erro inesperado ao buscar a imagem de perfil");
        }
    }

    @Override
    public DefaultDTO<Void> deleteClientPicture() {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        if(client.getClientPicture() != null) {
            try {
                this.minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(BUCKET_CLIENT_PICTURES)
                                .object(client.getClientPicture().getObjectId())
                                .build()
                );
            } catch (Exception e) {
                throw new FailedUploadFileException("Ocorreu um erro inesperado ao enviar sua imagem de perfil");
            }

            client.setClientPicture(null);
            this.clientRepository.save(client);
        }

        return new DefaultDTO<Void>(Boolean.TRUE, "Imagem perfil excluida com sucesso", null);
    }
}
