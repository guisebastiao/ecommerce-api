package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.discount.ApplyDiscountRequest;
import com.guisebastiao.ecommerceapi.dto.request.product.ProductRequest;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;
import com.guisebastiao.ecommerceapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<DefaultResponse<Void>> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        DefaultResponse<Void> response = this.productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/apply-discount")
    public ResponseEntity<DefaultResponse<Void>> applyDiscount(@RequestBody @Valid ApplyDiscountRequest applyDiscountRequest) {
        DefaultResponse<Void> response = this.productService.applyDiscount(applyDiscountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DefaultResponse<ProductResponse>> findProductById(@PathVariable String productId) {
        DefaultResponse<ProductResponse> response = this.productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<DefaultResponse<Void>> updateProduct(@PathVariable String productId, @RequestBody @Valid ProductRequest productRequest) {
        DefaultResponse<Void> response = this.productService.updateProduct(productId, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DefaultResponse<Void>> deleteProduct(@PathVariable String productId) {
        DefaultResponse<Void> response = this.productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}/remove-discount")
    public ResponseEntity<DefaultResponse<Void>> removeDiscount(@PathVariable String productId) {
        DefaultResponse<Void> response = this.productService.removeDiscount(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
