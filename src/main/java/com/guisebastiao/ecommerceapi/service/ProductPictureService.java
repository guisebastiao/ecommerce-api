package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;

public interface ProductPictureService {
    DefaultResponse<Void> deleteProductPicture(String productPictureId);
}
