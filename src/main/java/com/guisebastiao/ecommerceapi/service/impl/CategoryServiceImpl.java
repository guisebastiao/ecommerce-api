package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Category;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PagingDTO;
import com.guisebastiao.ecommerceapi.dto.request.CategoryRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CategoryResponseDTO;
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
    public DefaultDTO<Void> createCategory(CategoryRequestDTO categoryRequestDTO) {
        boolean existsCategory = this.categoryRepository.findByName(categoryRequestDTO.name()).isPresent();

        if(existsCategory) {
            throw new ConflictEntityException("Essa categoria já existe");
        }

        Category category = categoryMapper.toEntity(categoryRequestDTO);
        category.setName(categoryRequestDTO.name().toUpperCase());

        this.categoryRepository.save(category);

        return new DefaultDTO<Void>(Boolean.TRUE, "Categoria criada com sucesso", null);
    }

    @Override
    public DefaultDTO<PageResponseDTO<CategoryResponseDTO>> findAllCategories(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("name").ascending());

        Page<Category> resultPage = this.categoryRepository.findAll(pageable);

        PagingDTO pagingDTO = new PagingDTO(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<CategoryResponseDTO> dataResponse = resultPage.getContent().stream().map(this.categoryMapper::toDto).toList();

        PageResponseDTO<CategoryResponseDTO> data = new PageResponseDTO<CategoryResponseDTO>(dataResponse, pagingDTO);

        return new DefaultDTO<PageResponseDTO<CategoryResponseDTO>>(Boolean.TRUE, "Categorias retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> updateCategory(String categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category category = this.findById(categoryId);
        category.setName(categoryRequestDTO.name().toUpperCase());

        categoryRepository.save(category);

        return new DefaultDTO<Void>(Boolean.TRUE, "Categoria atualizada com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> deleteCategory(String categoryId) {
        Category category = this.findById(categoryId);

        boolean existsAssociatedProducts = category.getProducts().isEmpty();

        if(existsAssociatedProducts) {
            throw new ConflictEntityException("Essa categoria está associada a produtos e não pode ser excluída");
        }

        this.categoryRepository.delete(category);

        return new DefaultDTO<Void>(Boolean.TRUE, "Categoria excluida com sucesso", null);
    }

    private Category findById(String categoryId) {
        return this.categoryRepository.findById(LongConverter.toLong(categoryId))
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }
}
