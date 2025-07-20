package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ApplyDiscountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.ProductRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ProductResponseDTO;
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
    public ResponseEntity<DefaultDTO<Void>> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        DefaultDTO<Void> response = this.productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/apply-discount")
    public ResponseEntity<DefaultDTO<Void>> applyDiscount(@RequestBody @Valid ApplyDiscountRequestDTO applyDiscountRequestDTO) {
        DefaultDTO<Void> response = this.productService.applyDiscount(applyDiscountRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DefaultDTO<ProductResponseDTO>> findProductById(@PathVariable String productId) {
        DefaultDTO<ProductResponseDTO> response = this.productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<DefaultDTO<Void>> updateProduct(@PathVariable String productId, @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        DefaultDTO<Void> response = this.productService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DefaultDTO<Void>> deleteProduct(@PathVariable String productId) {
        DefaultDTO<Void> response = this.productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}/remove-discount")
    public ResponseEntity<DefaultDTO<Void>> removeDiscount(@PathVariable String productId) {
        DefaultDTO<Void> response = this.productService.removeDiscount(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
