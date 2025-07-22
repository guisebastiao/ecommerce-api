package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Favorite;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PagingDTO;
import com.guisebastiao.ecommerceapi.dto.request.FavoriteRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ProductResponseDTO;
import com.guisebastiao.ecommerceapi.exception.ConflictEntityException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.FavoriteMapper;
import com.guisebastiao.ecommerceapi.mapper.ProductMapper;
import com.guisebastiao.ecommerceapi.repository.FavoriteRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
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
    private ClientAuthProvider clientAuthProvider;

    @Override
    @Transactional
    public DefaultDTO<Void> createFavorite(FavoriteRequestDTO favoriteRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Product product = this.findProduct(favoriteRequestDTO.productId());

        boolean alreadyFavorite = this.favoriteRepository.alreadyFavorite(client.getId(), product.getId());

        if(alreadyFavorite) {
            throw new ConflictEntityException("Esse produto já está marcado como favorito");
        }

        Favorite favorite = this.favoriteMapper.toEntity(favoriteRequestDTO);
        favorite.setClient(client);
        favorite.setProduct(product);

        this.favoriteRepository.save(favorite);

        return new DefaultDTO<Void>(Boolean.TRUE, "Produto salvo como favorito", null);
    }

    @Override
    public DefaultDTO<PageResponseDTO<ProductResponseDTO>> findAllFavorites(int offset, int limit) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Pageable pageable = PageRequest.of(offset, limit, Sort.by("createdAt").descending());

        Page<Product> resultPage = this.favoriteRepository.findAllProductsFavoritesByClientId(client.getId(), pageable);

        PagingDTO pagingDTO = new PagingDTO(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<ProductResponseDTO> dataResponse = resultPage.getContent().stream().map(this.productMapper::toDto).toList();

        PageResponseDTO<ProductResponseDTO> data = new PageResponseDTO<ProductResponseDTO>(dataResponse, pagingDTO);

        return new DefaultDTO<PageResponseDTO<ProductResponseDTO>>(Boolean.TRUE, "Produtos favoritos retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> deleteFavorite(String favoriteId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Favorite favorite = this.findFavorite(favoriteId);

        if(!favorite.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para excluir essa item dos favoritos");
        }

        this.favoriteRepository.delete(favorite);

        return new DefaultDTO<Void>(Boolean.TRUE, "Produto removido dos favoritos com sucesso", null);
    }

    private Favorite findFavorite(String favoriteId) {
        return this.favoriteRepository.findById(UUIDConverter.toUUID(favoriteId))
                .orElseThrow(() -> new EntityNotFoundException("Produto favorito não encontrado"));
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }
}
