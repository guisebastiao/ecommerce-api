package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Cart;
import com.guisebastiao.ecommerceapi.domain.CartItem;
import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PagingDTO;
import com.guisebastiao.ecommerceapi.dto.request.CartItemRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CartItemResponseDTO;
import com.guisebastiao.ecommerceapi.exception.BadRequestException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.CartItemMapper;
import com.guisebastiao.ecommerceapi.repository.CartItemRepository;
import com.guisebastiao.ecommerceapi.repository.CartRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
import com.guisebastiao.ecommerceapi.service.CartService;
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
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientAuthProvider clientAuthProvider;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public DefaultDTO<Void> addProductToCart(CartItemRequestDTO cartRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Product product = this.findProduct(cartRequestDTO.productId());

        if (cartRequestDTO.quantity() > product.getStock()) {
            throw new BadRequestException("Quantidade de produto excede o estoque");
        }

        Cart cart = this.clientHasCart(client);

        CartItem cartItem = this.existingCartItem(cartRequestDTO.productId(), cartRequestDTO, client);

        if(cartItem.getId() != null){
            cartItem.setQuantity(cartItem.getQuantity() + cartRequestDTO.quantity());
        }

        cartItem.setCart(cart);
        cartItem.setProduct(product);

        this.cartItemRepository.save(cartItem);

        return new DefaultDTO<Void>(Boolean.TRUE, "Item adicionado para o carrinho", null);
    }

    @Override
    public DefaultDTO<PageResponseDTO<CartItemResponseDTO>> findAllCartItems(int offset, int limit) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Pageable pageable = PageRequest.of(offset, limit, Sort.by("createdAt").descending());

        Page<CartItem> resultPage = this.cartItemRepository.findAllByClientId(client.getId(), pageable);

        PagingDTO pagingDTO = new PagingDTO(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<CartItemResponseDTO> dataResponse = resultPage.getContent().stream().map(this.cartItemMapper::toDto).toList();

        PageResponseDTO<CartItemResponseDTO> data = new PageResponseDTO<CartItemResponseDTO>(dataResponse, pagingDTO);

        return new DefaultDTO<PageResponseDTO<CartItemResponseDTO>>(Boolean.TRUE, "Items do carrinho retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> removeProductFromCart(String cartItemId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        CartItem cartItem = this.findCartItem(cartItemId);

        if(!cartItem.getCart().getClient().getId().equals(client.getId())){
            throw new UnauthorizedException("Você não tem permissão para remover o produto do carrinho");
        }

        cartItem.setQuantity(cartItem.getQuantity() - 1);

        if(cartItem.getQuantity() <= 0){
            this.cartItemRepository.delete(cartItem);
        } else {
            this.cartItemRepository.save(cartItem);
        }

        return new DefaultDTO<Void>(Boolean.TRUE, "Produto removido do carrinho", null);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> removeAllProductsFromCart(String cartItemId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        System.out.println(cartItemId);

        CartItem cartItem = this.findCartItem(cartItemId);

        if(!cartItem.getCart().getClient().getId().equals(client.getId())){
            throw new UnauthorizedException("Você não tem permissão para remover o produto do carrinho");
        }

        this.cartItemRepository.delete(cartItem);

        return new DefaultDTO<Void>(Boolean.TRUE, "Produto removido do carrinho", null);
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    private CartItem existingCartItem(String productId, CartItemRequestDTO cartRequestDTO, Client client) {
        return this.cartItemRepository.findCartItemByProductId(UUIDConverter.toUUID(productId), client.getId())
                .orElse(this.cartItemMapper.toEntity(cartRequestDTO));
    }

    private Cart clientHasCart(Client client) {
        Cart existingCart = client.getCart();

        if (existingCart != null) return existingCart;

        Cart cart = new Cart();
        cart.setClient(client);
        client.setCart(cart);

        this.cartRepository.save(cart);
        return cart;
    }

    private CartItem findCartItem(String cartItemId) {
        return this.cartItemRepository.findById(UUIDConverter.toUUID(cartItemId))
                .orElseThrow(() -> new EntityNotFoundException("Item no carrinho não foi encontrado"));
    }
}
