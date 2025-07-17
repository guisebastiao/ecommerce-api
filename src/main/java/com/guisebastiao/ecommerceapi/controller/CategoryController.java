package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PaginationFilterDTO;
import com.guisebastiao.ecommerceapi.dto.request.CategoryRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CategoryResponseDTO;
import com.guisebastiao.ecommerceapi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<DefaultDTO<Void>> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        DefaultDTO<Void> response = this.categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultDTO<PageResponseDTO<CategoryResponseDTO>>> findAllCategories(@Valid PaginationFilterDTO pagination) {
        DefaultDTO<PageResponseDTO<CategoryResponseDTO>> response = this.categoryService.findAllCategories(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<DefaultDTO<Void>> updateCategory(@PathVariable String categoryId, @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        DefaultDTO<Void> response = this.categoryService.updateCategory(categoryId, categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<DefaultDTO<Void>> updateCategory(@PathVariable String categoryId) {
        DefaultDTO<Void> response = this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
