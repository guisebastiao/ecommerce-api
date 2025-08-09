package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.productPicture.ProductPictureRequest;

public interface ProductPictureService {
    DefaultResponse<Void> uploadProductPicture(String productId, ProductPictureRequest productPictureRequest);
    DefaultResponse<Void> deleteProductPicture(String productPictureId);
}
