package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Category;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.ProductRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ProductResponseDTO;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.mapper.ProductMapper;
import com.guisebastiao.ecommerceapi.repository.CategoryRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.service.ProductService;
import com.guisebastiao.ecommerceapi.util.LongConverter;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public DefaultDTO<Void> createProduct(ProductRequestDTO productRequestDTO) {
        Category category = this.findCategory(productRequestDTO.categoryId());

        Product product = this.productMapper.toEntity(productRequestDTO);
        product.setCategory(category);

        this.productRepository.save(product);
        return new DefaultDTO<Void>(Boolean.TRUE, "Produto criado com sucesso", null);
    }

    @Override
    public DefaultDTO<ProductResponseDTO> findProductById(String productId) {
        Product product = this.findProduct(productId);
        ProductResponseDTO productResponseDTO = this.productMapper.toDto(product);
        return new DefaultDTO<ProductResponseDTO>(Boolean.TRUE, "Produto encontrado com sucesso", productResponseDTO);
    }

    @Override
    public DefaultDTO<Void> updateProduct(String productId, ProductRequestDTO productRequestDTO) {
        Category category = this.findCategory(productRequestDTO.categoryId());

        Product product = this.findProduct(productId);

        product.setName(productRequestDTO.name());
        product.setDescription(productRequestDTO.description());
        product.setPrice(productRequestDTO.price());
        product.setStock(productRequestDTO.stock());
        product.setCategory(category);

        this.productRepository.save(product);

        return new DefaultDTO<Void>(Boolean.TRUE, "Produto atualizado com sucesso", null);
    }

    @Override
    public DefaultDTO<Void> deleteProduct(String productId) {
        Product product = this.findProduct(productId);
        this.productRepository.delete(product);
        return new DefaultDTO<Void>(Boolean.TRUE, "Produto excluido com sucesso", null);
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não foi encontrado"));
    }

    private Category findCategory(String categoryId) {
        return this.categoryRepository.findById(LongConverter.toLong(categoryId))
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }
}
