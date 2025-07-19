package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PaginationFilterDTO;
import com.guisebastiao.ecommerceapi.dto.request.DiscountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.DiscountResponseDTO;
import com.guisebastiao.ecommerceapi.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    public ResponseEntity<DefaultDTO<Void>> createDiscount(@RequestBody @Valid DiscountRequestDTO discountRequestDTO) {
        DefaultDTO<Void> response = this.discountService.createDiscount(discountRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultDTO<PageResponseDTO<DiscountResponseDTO>>> findAllDiscounts(@Valid PaginationFilterDTO pagination) {
        DefaultDTO<PageResponseDTO<DiscountResponseDTO>> response = this.discountService.findAllDiscounts(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<DefaultDTO<Void>> updateDiscount(@PathVariable String discountId, @RequestBody @Valid DiscountRequestDTO discountRequestDTO) {
        DefaultDTO<Void> response = this.discountService.updateDiscount(discountId, discountRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DefaultDTO<Void>> deleteDiscount(@PathVariable String discountId) {
        DefaultDTO<Void> response = this.discountService.deleteDiscount(discountId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
