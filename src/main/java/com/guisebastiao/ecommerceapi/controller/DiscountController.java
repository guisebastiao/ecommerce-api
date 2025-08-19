package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.discount.DiscountRequest;
import com.guisebastiao.ecommerceapi.dto.response.discount.DiscountResponse;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;
import com.guisebastiao.ecommerceapi.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    public ResponseEntity<DefaultResponse<Void>> createDiscount(@RequestBody @Valid DiscountRequest discountRequest) {
        DefaultResponse<Void> response = this.discountService.createDiscount(discountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<PageResponse<DiscountResponse>>> findAllDiscounts(@Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<DiscountResponse>> response = this.discountService.findAllDiscounts(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{discountId}/products")
    public ResponseEntity<DefaultResponse<PageResponse<ProductResponse>>> findAllProductsWithDiscounts(@PathVariable String discountId, @Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<ProductResponse>> response = this.discountService.findAllProductsWithDiscounts(discountId, pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DefaultResponse<Void>> updateDiscount(@PathVariable String discountId, @RequestBody @Valid DiscountRequest discountRequest) {
        DefaultResponse<Void> response = this.discountService.updateDiscount(discountId, discountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<DefaultResponse<Void>> deleteDiscount(@PathVariable String discountId) {
        DefaultResponse<Void> response = this.discountService.deleteDiscount(discountId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
