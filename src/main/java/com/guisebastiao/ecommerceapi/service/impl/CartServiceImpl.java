package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.*;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.cart.CartItemRequest;
import com.guisebastiao.ecommerceapi.dto.request.cart.UpdateCartItemRequest;
import com.guisebastiao.ecommerceapi.dto.response.cart.CartItemResponse;
import com.guisebastiao.ecommerceapi.dto.response.cart.CartResponse;
import com.guisebastiao.ecommerceapi.exception.BadRequestException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.CartItemMapper;
import com.guisebastiao.ecommerceapi.repository.CartItemRepository;
import com.guisebastiao.ecommerceapi.repository.CartRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import com.guisebastiao.ecommerceapi.service.CartService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private AuthProvider clientAuthProvider;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public DefaultResponse<Void> addProductToCart(CartItemRequest cartRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Product product = this.findProduct(cartRequest.productId());

        if (cartRequest.quantity() > product.getStock()) {
            throw new BadRequestException("Quantidade de produto excede o estoque");
        }

        Cart cart = this.clientHasCart(client);

        CartItem cartItem = this.existingCartItem(cartRequest.productId(), cartRequest, client);

        if(cartItem.getId() != null){
            cartItem.setQuantity(cartItem.getQuantity() + cartRequest.quantity());
        }

        cartItem.setCart(cart);
        cartItem.setProduct(product);

        this.cartItemRepository.save(cartItem);

        return new DefaultResponse<Void>(true, "Item adicionado para o carrinho", null);
    }

    @Override
    public DefaultResponse<CartResponse> findAllCartItems() {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        List<CartItem> cartItems = this.cartItemRepository.findAllByClientId(client.getId());

        BigDecimal total = this.calculateTotal(cartItems);
        BigDecimal subtotal = this.calculateSubtotal(cartItems);
        BigDecimal discounts = this.calculateTotalDiscounts(cartItems);

        List<CartItemResponse> cartItemsDTO = cartItems.stream().map(this.cartItemMapper::toDTO).toList();

        CartResponse data = new CartResponse(cartItemsDTO, total, subtotal, discounts);

        return new DefaultResponse<CartResponse>(true, "Items do carrinho retornados com sucesso", data);
    }

    @Override
    public DefaultResponse<Void> updateQuantity(String cartItemId, UpdateCartItemRequest updateCartItemRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        CartItem cartItem = this.findCartItem(cartItemId);

        if(!cartItem.getCart().getClient().getId().equals(client.getId())){
            throw new UnauthorizedException("Você não tem permissão para remover o produto do carrinho");
        }

        cartItem.setQuantity(updateCartItemRequest.quantity());
        this.cartItemRepository.save(cartItem);

        return new DefaultResponse<Void>(true, "Quantidade do produto foi alterada com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> removeProductFromCart(String cartItemId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        CartItem cartItem = this.findCartItem(cartItemId);

        if(!cartItem.getCart().getClient().getId().equals(client.getId())){
            throw new UnauthorizedException("Você não tem permissão para remover o produto do carrinho");
        }

        cartItem.setQuantity(cartItem.getQuantity() - 1);

        if(cartItem.getQuantity() <= 0){
            cartItem.getCart().getItems().remove(cartItem);
            this.cartItemRepository.delete(cartItem);
        } else {
            this.cartItemRepository.save(cartItem);
        }

        return new DefaultResponse<Void>(true, "Produto removido do carrinho", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> removeAllProductsFromCart(String cartItemId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        CartItem cartItem = this.findCartItem(cartItemId);

        if(!cartItem.getCart().getClient().getId().equals(client.getId())){
            throw new UnauthorizedException("Você não tem permissão para remover o produto do carrinho");
        }

        Cart cart = cartItem.getCart();
        cart.getItems().remove(cartItem);
        this.cartItemRepository.delete(cartItem);

        this.cartRepository.save(cart);

        return new DefaultResponse<Void>(true, "Produto removido do carrinho", null);
    }

    private BigDecimal calculateSubtotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> {
                    BigDecimal originalPrice = item.getProduct().getPrice();
                    Discount discount = item.getProduct().getDiscount();
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());

                    BigDecimal unitPrice = originalPrice;

                    if (discount != null && discount.getPercent() != null) {
                        BigDecimal discountPercent = BigDecimal.valueOf(discount.getPercent());
                        BigDecimal discountFactor = BigDecimal.ONE.subtract(
                                discountPercent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                        );
                        unitPrice = originalPrice.multiply(discountFactor);
                    }

                    return unitPrice.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalDiscounts(List<CartItem> cartItems) {
        BigDecimal subtotal = calculateSubtotal(cartItems);
        BigDecimal total = calculateTotal(cartItems);
        return subtotal.subtract(total);
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    private CartItem existingCartItem(String productId, CartItemRequest cartRequest, Client client) {
        return this.cartItemRepository.findCartItemByProductId(UUIDConverter.toUUID(productId), client.getId())
                .orElse(this.cartItemMapper.toEntity(cartRequest));
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
