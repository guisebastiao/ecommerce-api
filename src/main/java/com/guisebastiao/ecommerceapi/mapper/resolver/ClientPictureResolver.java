package com.guisebastiao.ecommerceapi.mapper.resolver;

import com.guisebastiao.ecommerceapi.domain.ClientPicture;
import com.guisebastiao.ecommerceapi.exception.FailedUploadFileException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.guisebastiao.ecommerceapi.config.MinioConfig.BUCKET_CLIENT_PICTURES;

@Component
public class ClientPictureResolver {

    @Autowired
    private MinioClient minioClient;

    @Named("resolvePictureUrl")
    public String resolvePictureUrl(ClientPicture clientPicture) {
        if (clientPicture == null) return null;

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(BUCKET_CLIENT_PICTURES)
                            .object(clientPicture.getObjectId())
                            .expiry(604800)
                            .build()
            );
        } catch (Exception e) {
            throw new FailedUploadFileException("Erro ao gerar URL da imagem de perfil");
        }
    }
}