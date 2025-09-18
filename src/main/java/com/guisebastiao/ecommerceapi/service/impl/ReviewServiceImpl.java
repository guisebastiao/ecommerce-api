package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.domain.Review;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.Paging;
import com.guisebastiao.ecommerceapi.dto.request.review.ReviewRequest;
import com.guisebastiao.ecommerceapi.dto.response.review.ReviewResponse;
import com.guisebastiao.ecommerceapi.exception.ConflictEntityException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.ReviewMapper;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.repository.ReviewRepository;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import com.guisebastiao.ecommerceapi.service.ReviewService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthProvider clientAuthProvider;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    @Transactional
    public DefaultResponse<Void> createReview(String productId, ReviewRequest reviewRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Product product = this.findProduct(productId);

        boolean isReviewed = product.getReviews().stream().anyMatch(e -> e.getClient().getId().equals(client.getId()));

        if(isReviewed) {
            throw new ConflictEntityException("Você já avaliou esse produto");
        }

        Review review = this.reviewMapper.toEntity(reviewRequest);
        review.setClient(client);
        review.setProduct(product);

        this.reviewRepository.save(review);

        return new DefaultResponse<Void>(true, "Avaliação criada com sucesso", null);
    }

    @Override
    public DefaultResponse<PageResponse<ReviewResponse>> findAllReviews(String productId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by("createdAt").descending());

        Page<Review> resultPage = this.reviewRepository.findAll(pageable);

        Paging paging = new Paging(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<ReviewResponse> dataResponse = resultPage.getContent().stream().map(this.reviewMapper::toDTO).toList();

        PageResponse<ReviewResponse> data = new PageResponse<ReviewResponse>(dataResponse, paging);

        return new DefaultResponse<PageResponse<ReviewResponse>>(Boolean.TRUE, "Avaliações retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteReview(String productId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Review review = this.reviewRepository.findByClientAndProduct(client.getId(), UUIDConverter.toUUID(productId));

        if(!review.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para excluir essa avaliação");
        }

        this.reviewRepository.delete(review);

        return new DefaultResponse<Void>(true, "Avaliação excluida com sucesso", null);
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
