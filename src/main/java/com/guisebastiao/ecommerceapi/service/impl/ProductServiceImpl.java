package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.config.MinioConfig;
import com.guisebastiao.ecommerceapi.domain.*;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.Paging;
import com.guisebastiao.ecommerceapi.dto.request.discount.ApplyDiscountRequest;
import com.guisebastiao.ecommerceapi.dto.request.product.ProductRequest;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;
import com.guisebastiao.ecommerceapi.exception.BadRequestException;
import com.guisebastiao.ecommerceapi.exception.ConflictEntityException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.FailedUploadFileException;
import com.guisebastiao.ecommerceapi.mapper.ProductMapper;
import com.guisebastiao.ecommerceapi.repository.CategoryRepository;
import com.guisebastiao.ecommerceapi.repository.DiscountRepository;
import com.guisebastiao.ecommerceapi.repository.ProductPictureRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.service.ProductService;
import com.guisebastiao.ecommerceapi.util.CodeGenerator;
import com.guisebastiao.ecommerceapi.util.LongConverter;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPictureRepository productPictureRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private CodeGenerator codeGenerator;

    @Override
    @Transactional
    public DefaultResponse<Void> createProduct(ProductRequest productRequest) {
        Category category = this.findCategory(productRequest.categoryId());

        List<MultipartFile> files = productRequest.files();
        if (files.size() > 20) {
            throw new BadRequestException("Um produto pode ter no máximo 20 imagens");
        }

        Product product = this.productMapper.toEntity(productRequest);
        product.setCategory(category);

        Product savedProduct = this.productRepository.save(product);

        List<ProductPicture> productPictures = files.stream().map(file -> {
            String objectId = this.codeGenerator.generateToken();
            String contentType = file.getContentType();

            try (InputStream inputStream = file.getInputStream()) {
                this.minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioConfig.getMinioBucket())
                                .object(minioConfig.getProductPicturesFolder() + objectId)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(contentType)
                                .build()
                );
            } catch (Exception e) {
                throw new FailedUploadFileException("Erro ao enviar imagem do produto", e);
            }

            ProductPicture picture = new ProductPicture();
            picture.setObjectId(objectId);
            picture.setProduct(savedProduct);
            return picture;
        }).toList();

        this.productPictureRepository.saveAll(productPictures);

        return new DefaultResponse<Void>(true, "Produto criado com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> applyDiscount(ApplyDiscountRequest applyDiscountRequest) {
        Discount discount = this.findDiscount(applyDiscountRequest.discountId());
        Product product = this.findProduct(applyDiscountRequest.productId());

        if (product.getDiscount() != null) {
            throw new ConflictEntityException("Esse produto já está com desconto");
        }

        product.setDiscount(discount);

        productRepository.save(product);

        return new DefaultResponse<Void>(true, "Desconto aplicado com sucesso", null);
    }

    @Override
    public DefaultResponse<ProductResponse> findProductById(String productId) {
        Product product = this.findProduct(productId);
        ProductResponse productResponse = this.productMapper.toDTO(product);
        return new DefaultResponse<ProductResponse>(true, "Produto encontrado com sucesso", productResponse);
    }

    @Override
    public DefaultResponse<PageResponse<ProductResponse>> findAllProducts(String search, String category, PaginationFilter pagination) {
        Pageable pageable = PageRequest.of(pagination.offset() - 1, pagination.limit());

        Page<Product> resultPage = this.productRepository.findAllBySearchAndCategory(search, category, pageable);

        Paging paging = new Paging(resultPage.getTotalElements(), resultPage.getTotalPages(), pagination.offset(), pagination.limit());

        List<ProductResponse> dataResponse = resultPage.getContent().stream().map(this.productMapper::toDTO).toList();

        PageResponse<ProductResponse> data = new PageResponse<ProductResponse>(dataResponse, paging);

        return new DefaultResponse<PageResponse<ProductResponse>>(true, "Produtos retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> updateProduct(String productId, ProductRequest productRequest) {
        Category category = this.findCategory(productRequest.categoryId());

        Product product = this.findProduct(productId);

        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setStock(productRequest.stock());
        product.setCategory(category);

        this.productRepository.save(product);

        return new DefaultResponse<Void>(true, "Produto atualizado com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteProduct(String productId) {
        Product product = this.findProduct(productId);
        this.productRepository.delete(product);
        return new DefaultResponse<Void>(true, "Produto excluido com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> removeDiscount(String productId) {
        Product product = this.findProduct(productId);

        product.setDiscount(null);
        this.productRepository.save(product);

        return new DefaultResponse<Void>(true, "Desconto removido com sucesso", null);
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não foi encontrado"));
    }

    private Category findCategory(String categoryId) {
        return this.categoryRepository.findById(LongConverter.toLong(categoryId))
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }

    private Discount findDiscount(String discountId) {
        return this.discountRepository.findById(UUIDConverter.toUUID(discountId))
                .orElseThrow(() -> new EntityNotFoundException("Disconto não encontrado"));
    }
}
