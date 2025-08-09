package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.category.CategoryRequest;
import com.guisebastiao.ecommerceapi.dto.response.category.CategoryResponse;
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
    public ResponseEntity<DefaultResponse<Void>> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        DefaultResponse<Void> response = this.categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<PageResponse<CategoryResponse>>> findAllCategories(@Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<CategoryResponse>> response = this.categoryService.findAllCategories(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<DefaultResponse<Void>> updateCategory(@PathVariable String categoryId, @RequestBody @Valid CategoryRequest categoryRequest) {
        DefaultResponse<Void> response = this.categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<DefaultResponse<Void>> updateCategory(@PathVariable String categoryId) {
        DefaultResponse<Void> response = this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
