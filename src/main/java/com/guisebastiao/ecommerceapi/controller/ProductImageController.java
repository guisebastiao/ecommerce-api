package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ProductPictureRequestDTO;
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
    public ResponseEntity<DefaultDTO<Void>> uploadProductPicture(@PathVariable String productId, @ModelAttribute @Valid ProductPictureRequestDTO productPictureRequestDTO) {
        DefaultDTO<Void> response = this.productPictureService.uploadProductPicture(productId, productPictureRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{productPictureId}")
    public ResponseEntity<DefaultDTO<Void>> deleteProductPicture(@PathVariable String productPictureId) {
        DefaultDTO<Void> response = this.productPictureService.deleteProductPicture(productPictureId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
