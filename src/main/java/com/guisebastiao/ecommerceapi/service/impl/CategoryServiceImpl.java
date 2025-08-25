package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Category;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.Paging;
import com.guisebastiao.ecommerceapi.dto.request.category.CategoryRequest;
import com.guisebastiao.ecommerceapi.dto.response.category.CategoryResponse;
import com.guisebastiao.ecommerceapi.exception.ConflictEntityException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.mapper.CategoryMapper;
import com.guisebastiao.ecommerceapi.repository.CategoryRepository;
import com.guisebastiao.ecommerceapi.service.CategoryService;
import com.guisebastiao.ecommerceapi.util.LongConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public DefaultResponse<Void> createCategory(CategoryRequest categoryRequest) {
        boolean existsCategory = this.categoryRepository.findByName(categoryRequest.name()).isPresent();

        if(existsCategory) {
            throw new ConflictEntityException("Essa categoria já existe");
        }

        Category category = categoryMapper.toEntity(categoryRequest);
        category.setName(categoryRequest.name().toUpperCase());

        this.categoryRepository.save(category);

        return new DefaultResponse<Void>(true, "Categoria criada com sucesso", null);
    }

    @Override
    public DefaultResponse<PageResponse<CategoryResponse>> findAllCategories(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by("name").ascending());

        Page<Category> resultPage = this.categoryRepository.findAll(pageable);

        Paging paging = new Paging(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<CategoryResponse> dataResponse = resultPage.getContent().stream().map(this.categoryMapper::toDTO).toList();

        PageResponse<CategoryResponse> data = new PageResponse<CategoryResponse>(dataResponse, paging);

        return new DefaultResponse<PageResponse<CategoryResponse>>(true, "Categorias retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> updateCategory(String categoryId, CategoryRequest categoryRequest) {
        Category category = this.findById(categoryId);
        category.setName(categoryRequest.name().toUpperCase());

        categoryRepository.save(category);

        return new DefaultResponse<Void>(true, "Categoria atualizada com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteCategory(String categoryId) {
        Category category = this.findById(categoryId);

        boolean existsAssociatedProducts = category.getProducts().isEmpty();

        if(existsAssociatedProducts) {
            throw new ConflictEntityException("Essa categoria está associada a produtos e não pode ser excluída");
        }

        this.categoryRepository.delete(category);

        return new DefaultResponse<Void>(true, "Categoria excluida com sucesso", null);
    }

    private Category findById(String categoryId) {
        return this.categoryRepository.findById(LongConverter.toLong(categoryId))
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }
}
