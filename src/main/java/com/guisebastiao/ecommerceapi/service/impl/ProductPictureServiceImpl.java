package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.config.MinioConfig;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.domain.ProductPicture;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.productPicture.ProductPictureRequest;
import com.guisebastiao.ecommerceapi.exception.BadRequestException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.FailedUploadFileException;
import com.guisebastiao.ecommerceapi.repository.ProductPictureRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.service.ProductPictureService;
import com.guisebastiao.ecommerceapi.util.CodeGenerator;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class ProductPictureServiceImpl implements ProductPictureService {

    @Autowired
    private ProductPictureRepository productPictureRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private CodeGenerator codeGenerator;

    @Override
    @Transactional
    public DefaultResponse<Void> uploadProductPicture(String productId, ProductPictureRequest productPictureRequest) {
        Product product = this.findProduct(productId);

        if(product.getProductPictures().size() > 20) {
            throw new BadRequestException("Um produto pode ter no máximo 20 imagens");
        }

        List<MultipartFile> files = productPictureRequest.files();

        List<ProductPicture> productPictures = files.stream().map(file -> {
            String objectId = this.codeGenerator.generateToken();
            String contentType = file.getContentType();

            try {
                InputStream inputStream = file.getInputStream();

                this.minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioConfig.getMinioBucket())
                                .object(minioConfig.getProductPicturesFolder() + objectId)
                                .stream(inputStream, inputStream.available(), -1)
                                .contentType(contentType)
                                .build()
                );
            } catch (Exception e) {
                throw new FailedUploadFileException("Ocorreu um erro inesperado ao enviar sua imagem de perfil", e);
            }

            ProductPicture productPicture = new ProductPicture();
            productPicture.setObjectId(objectId);
            productPicture.setProduct(product);

            return productPicture;
        }).toList();

        this.productPictureRepository.saveAll(productPictures);

        return new DefaultResponse<Void>(true, "Imagens do produto salvas com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteProductPicture(String productPictureId) {
        ProductPicture productPicture = this.findProductPicture(productPictureId);

        if(productPicture.getProduct().getProductPictures().size() <= 1) {
            throw new BadRequestException("Você não pode excluir a única imagem do produto");
        }

        this.productPictureRepository.delete(productPicture);

        return new DefaultResponse<Void>(true, "Imagem do produto excluida com sucesso", null);
    }

    private ProductPicture findProductPicture(String productPictureId) {
        return this.productPictureRepository.findById(UUIDConverter.toUUID(productPictureId))
                .orElseThrow(() -> new EntityNotFoundException("Imagem do produto não foi encontrada"));
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não foi encontrado"));
    }
}
