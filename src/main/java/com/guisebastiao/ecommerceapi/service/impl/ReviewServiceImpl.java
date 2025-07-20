package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.domain.Review;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PagingDTO;
import com.guisebastiao.ecommerceapi.dto.request.ReviewRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ReviewResponseDTO;
import com.guisebastiao.ecommerceapi.exception.ConflictEntityException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.ReviewMapper;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.repository.ReviewRepository;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
import com.guisebastiao.ecommerceapi.service.ReviewService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientAuthProvider clientAuthProvider;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public DefaultDTO<Void> createReview(String productId, ReviewRequestDTO reviewRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Product product = this.findProduct(productId);

        boolean isReviewed = product.getReviews().stream().anyMatch(e -> e.getClient().getId().equals(client.getId()));

        if(isReviewed) {
            throw new ConflictEntityException("Você já avaliou esse produto");
        }

        Review review = this.reviewMapper.toEntity(reviewRequestDTO);
        review.setClient(client);
        review.setProduct(product);

        this.reviewRepository.save(review);

        return new DefaultDTO<Void>(Boolean.TRUE, "Avaliação criada com sucesso", null);
    }

    @Override
    public DefaultDTO<PageResponseDTO<ReviewResponseDTO>> findAllReviews(String productId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);

        Page<Review> resultPage = this.reviewRepository.findAll(pageable);

        PagingDTO pagingDTO = new PagingDTO(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<ReviewResponseDTO> dataResponse = resultPage.getContent().stream().map(this.reviewMapper::toDto).toList();

        PageResponseDTO<ReviewResponseDTO> data = new PageResponseDTO<ReviewResponseDTO>(dataResponse, pagingDTO);

        return new DefaultDTO<PageResponseDTO<ReviewResponseDTO>>(Boolean.TRUE, "Avaliações retornados com sucesso", data);
    }

    @Override
    public DefaultDTO<Void> deleteReview(String reviewId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Review review = this.findReview(reviewId);

        if(!review.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para excluir essa avaliação");
        }

        this.reviewRepository.delete(review);

        return new DefaultDTO<Void>(Boolean.TRUE, "Avaliação excluida com sucesso", null);
    }

    private Review findReview(String reviewId) {
        return this.reviewRepository.findById(UUIDConverter.toUUID(reviewId))
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada"));
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }
}
