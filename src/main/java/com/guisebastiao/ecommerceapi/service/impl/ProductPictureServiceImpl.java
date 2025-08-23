package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.domain.ProductPicture;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.exception.BadRequestException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.repository.ProductPictureRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.service.ProductPictureService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPictureServiceImpl implements ProductPictureService {

    @Autowired
    private ProductPictureRepository productPictureRepository;

    @Autowired
    private ProductRepository productRepository;

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
