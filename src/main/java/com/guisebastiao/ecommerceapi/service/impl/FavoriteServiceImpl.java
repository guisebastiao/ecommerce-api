package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Favorite;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.Paging;
import com.guisebastiao.ecommerceapi.dto.request.favorite.FavoriteRequest;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;
import com.guisebastiao.ecommerceapi.exception.BadRequestException;
import com.guisebastiao.ecommerceapi.exception.ConflictEntityException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.FavoriteMapper;
import com.guisebastiao.ecommerceapi.mapper.ProductMapper;
import com.guisebastiao.ecommerceapi.repository.FavoriteRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import com.guisebastiao.ecommerceapi.service.FavoriteService;
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
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AuthProvider clientAuthProvider;

    @Override
    @Transactional
    public DefaultResponse<Void> createFavorite(FavoriteRequest favoriteRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Product product = this.findProduct(favoriteRequest.productId());

        boolean alreadyFavorite = this.favoriteRepository.alreadyFavorite(client.getId(), product.getId());

        if(alreadyFavorite) {
            throw new ConflictEntityException("Esse produto já está marcado como favorito");
        }

        Favorite favorite = this.favoriteMapper.toEntity(favoriteRequest);
        favorite.setClient(client);
        favorite.setProduct(product);

        this.favoriteRepository.save(favorite);

        return new DefaultResponse<Void>(true, "Produto salvo como favorito", null);
    }

    @Override
    public DefaultResponse<PageResponse<ProductResponse>> findAllFavorites(int offset, int limit) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Pageable pageable = PageRequest.of(offset, limit, Sort.by("createdAt").descending());

        Page<Product> resultPage = this.favoriteRepository.findAllProductsFavoritesByClientId(client.getId(), pageable);

        Paging paging = new Paging(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<ProductResponse> dataResponse = resultPage.getContent().stream().map(this.productMapper::toDTO).toList();

        PageResponse<ProductResponse> data = new PageResponse<ProductResponse>(dataResponse, paging);

        return new DefaultResponse<PageResponse<ProductResponse>>(true, "Produtos favoritos retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteFavorite(String productId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Product product = this.findProduct(productId);

        Favorite favorite = favoriteRepository.findByClientIdAndProductId(client.getId(), product.getId())
                .orElseThrow(() -> new BadRequestException("Você não tem esse produto como favorito"));

        if(!favorite.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para excluir essa item dos favoritos");
        }

        this.favoriteRepository.delete(favorite);

        return new DefaultResponse<Void>(true, "Produto removido dos favoritos com sucesso", null);
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }
}
