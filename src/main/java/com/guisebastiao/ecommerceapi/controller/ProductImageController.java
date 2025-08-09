package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.productPicture.ProductPictureRequest;
import com.guisebastiao.ecommerceapi.service.ProductPictureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-picture")
public class ProductImageController {

    @Autowired
    private ProductPictureService productPictureService;

    @PostMapping("/{productId}")
    public ResponseEntity<DefaultResponse<Void>> uploadProductPicture(@PathVariable String productId, @ModelAttribute @Valid ProductPictureRequest productPictureRequest) {
        DefaultResponse<Void> response = this.productPictureService.uploadProductPicture(productId, productPictureRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{productPictureId}")
    public ResponseEntity<DefaultResponse<Void>> deleteProductPicture(@PathVariable String productPictureId) {
        DefaultResponse<Void> response = this.productPictureService.deleteProductPicture(productPictureId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
