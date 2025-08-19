package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.*;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.response.order.OrderResponse;
import com.guisebastiao.ecommerceapi.dto.request.order.OrderRequest;
import com.guisebastiao.ecommerceapi.dto.request.order.PaymentRequest;
import com.guisebastiao.ecommerceapi.enums.OrderStatus;
import com.guisebastiao.ecommerceapi.enums.PaymentMethod;
import com.guisebastiao.ecommerceapi.enums.PaymentStatus;
import com.guisebastiao.ecommerceapi.exception.BadGatewayException;
import com.guisebastiao.ecommerceapi.exception.BadRequestException;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.repository.*;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import com.guisebastiao.ecommerceapi.service.OrderService;
import com.guisebastiao.ecommerceapi.service.StripePaymentService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private StripePaymentService stripePaymentService;

    @Autowired
    private AuthProvider authProvider;

    @Override
    @Transactional
    public DefaultResponse<OrderResponse> createOrder(OrderRequest orderRequest) {
        try {
            Client client = authProvider.getClientAuthenticated();
            Cart cart = validateAndGetCart(client);

            validateStock(cart.getItems());

            Order order = buildOrder(client, cart);
            BigDecimal totalAmount = calculateTotalAmount(order.getItems());

            String paymentIntentId = stripePaymentService.createPaymentIntent(totalAmount);
            order.setPaymentIntentId(paymentIntentId);

            Order savedOrder = orderRepository.save(order);

            OrderResponse response = buildOrderResponse(paymentIntentId, savedOrder);

            return new DefaultResponse<>(true, "Pedido criado, aguardando pagamento", response);

        } catch (Exception e) {
            throw new BadGatewayException("Erro interno ao criar pedido", e);
        }
    }

    @Override
    @Transactional
    public DefaultResponse<Void> payOrder(String orderId, PaymentRequest paymentRequest) {
        try {
            Order order = findOrderById(orderId);
            validateOrderForPayment(order);

            PaymentStatus paymentStatus = stripePaymentService.confirmPayment(
                    order.getPaymentIntentId(),
                    paymentRequest.stripePaymentId()
            );

            return handlePaymentResult(order, paymentStatus);

        } catch (Exception e) {
            throw new BadGatewayException("Erro interno ao processar pagamento", e);
        }
    }

    @Override
    public DefaultResponse<Void> cancelOrder(String orderId) {
        try {
            Order order = findOrderById(orderId);

            if (order.getOrderStatus() == OrderStatus.PAID) {
                return new DefaultResponse<Void>(false, "Pedido já foi pago e não pode ser cancelado", null);
            }

            if (order.getOrderStatus() == OrderStatus.CANCELED) {
                return new DefaultResponse<Void>(false, "Pedido já está cancelado", null);
            }

            if (order.getPaymentIntentId() != null) {
                stripePaymentService.cancelPaymentIntent(order.getPaymentIntentId());
            }

            order.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.save(order);

            return new DefaultResponse<>(true, "Pedido cancelado com sucesso", null);
        } catch (Exception e) {
            throw new BadRequestException("Erro ao cancelar pedido");
        }
    }

    private String generateOrderNumber() {
        return RandomStringUtils.randomAlphanumeric(12).toUpperCase();
    }

    private Cart validateAndGetCart(Client client) {
        Cart cart = client.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new EntityNotFoundException("Carrinho vazio ou não encontrado");
        }

        return cart;
    }

    private void validateStock(List<CartItem> cartItems) {
        List<String> outOfStockProducts = cartItems.stream()
                .filter(item -> item.getQuantity() > item.getProduct().getStock())
                .map(item -> item.getProduct().getName())
                .toList();

        if (!outOfStockProducts.isEmpty()) {
            throw new BadRequestException("Produtos sem estoque suficiente");
        }
    }

    private Order buildOrder(Client client, Cart cart) {
        Order order = new Order();
        order.setClient(client);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderNumber(generateOrderNumber());
        order.setPaymentMethod(PaymentMethod.CARD);

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> buildOrderItem(cartItem, order))
                .toList();

        order.setItems(orderItems);
        return order;
    }

    private OrderItem buildOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setOrder(order);
        return orderItem;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> {
                    BigDecimal originalPrice = item.getProduct().getPrice();
                    Discount discount = item.getProduct().getDiscount();
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());

                    BigDecimal unitPrice = originalPrice;

                    if (discount != null && discount.getPercent() != null) {
                        BigDecimal discountPercent = BigDecimal.valueOf(discount.getPercent());
                        BigDecimal discountFactor = BigDecimal.ONE.subtract(discountPercent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
                        unitPrice = originalPrice.multiply(discountFactor);
                    }

                    return unitPrice.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderResponse buildOrderResponse(String paymentIntentId, Order order) {
        String clientSecret = stripePaymentService.getClientSecret(paymentIntentId);
        return new OrderResponse(clientSecret, paymentIntentId, order.getId());
    }

    private Order findOrderById(String orderId) {
        return orderRepository.findById(UUIDConverter.toUUID(orderId))
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado: " + orderId));
    }

    private void validateOrderForPayment(Order order) {
        switch (order.getOrderStatus()) {
            case PAID -> throw new BadRequestException("Pedido já foi pago");
            case CANCELED -> throw new BadRequestException("Pedido cancelado não pode ser pago");
            case FAILED -> throw new BadRequestException("Pedido com falha não pode ser pago");
        }
    }

    private DefaultResponse<Void> handlePaymentResult(Order order, PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case SUCCEEDED -> {
                processSuccessfulPayment(order);
                yield new DefaultResponse<>(true, "Pagamento efetuado com sucesso", null);
            }
            case REQUIRES_ACTION -> {
                order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
                orderRepository.save(order);
                yield new DefaultResponse<>(false, "Pagamento requer ação adicional", null);
            }
            case PROCESSING -> {
                order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
                orderRepository.save(order);
                yield new DefaultResponse<>(true, "Pagamento está sendo processado", null);
            }
            case FAILED -> {
                order.setOrderStatus(OrderStatus.FAILED);
                orderRepository.save(order);
                yield new DefaultResponse<>(false, "Falha no pagamento", null);
            }
        };
    }

    private void processSuccessfulPayment(Order order) {
        clearClientCart(order.getClient());
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order);
        updateProductStock(order.getItems());
    }

    private void clearClientCart(Client client) {
        Cart cart = client.getCart();
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private void updateProductStock(List<OrderItem> orderItems) {
        List<Product> productsToUpdate = orderItems.stream()
                .map(item -> {
                    Product product = item.getProduct();
                    int newStock = product.getStock() - item.getQuantity();
                    product.setStock(newStock);
                    return product;
                })
                .toList();

        productRepository.saveAll(productsToUpdate);
    }
}
