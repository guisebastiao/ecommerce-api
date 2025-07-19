package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ProductPictureRequestDTO;

public interface ProductPictureService {
    DefaultDTO<Void> uploadProductPicture(String productId, ProductPictureRequestDTO productPictureRequestDTO);
    DefaultDTO<Void> deleteProductPicture(String productPictureId);
}
