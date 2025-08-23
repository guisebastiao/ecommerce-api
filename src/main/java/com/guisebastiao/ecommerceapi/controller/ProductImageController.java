package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.service.ProductPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-picture")
public class ProductImageController {

    @Autowired
    private ProductPictureService productPictureService;

    @DeleteMapping("/{productPictureId}")
    public ResponseEntity<DefaultResponse<Void>> deleteProductPicture(@PathVariable String productPictureId) {
        DefaultResponse<Void> response = this.productPictureService.deleteProductPicture(productPictureId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
